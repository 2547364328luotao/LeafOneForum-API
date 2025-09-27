package xyz.luotao.v1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 基于 Redis ZSET 的“心跳 + 在线统计”服务。
 *
 * 设计要点：
 * 1) 使用 ZSET 存储：member = 用户ID，score = 最后一次心跳的毫秒时间戳。
 * 2) 客户端定期上报心跳：ZADD key score member（相同 member 会被覆盖为最新 score）。
 * 3) 统计在线人数时：先清理超时成员，再统计 ZCARD。
 *    为了避免并发下的误差，这一步使用 Lua 脚本一次性完成（原子性）。
 * 4) 支持多实例：所有应用实例只要连到同一个 Redis，读写同一个 ZSET key，即可天然横向扩展。
 */
@Service
public class HeartbeatService {

    private static final Logger log = LoggerFactory.getLogger(HeartbeatService.class);

    private final StringRedisTemplate redis;

    /**
     * Key 前缀，可按需修改。不同“业务/房间/渠道”使用不同 namespace 拼接成不同 key。
     * 最终 key = KEY_PREFIX + namespace
     */
    private static final String KEY_PREFIX = "online:hb:";

    /**
     * Lua 脚本：
     * - 入参 KEYS[1] = ZSET key
     * - 入参 ARGV[1] = 过期阈值（阈值毫秒时间戳，<= 该时间戳的成员都视为过期）
     * 步骤：
     *   1) ZREMRANGEBYSCORE key 0 threshold  -- 清理所有超时成员
     *   2) ZCARD key                         -- 返回当前在线成员数量
     */
    private static final String CLEANUP_AND_COUNT_LUA =
            "local key = KEYS[1]\n" +
            "local threshold = tonumber(ARGV[1])\n" +
            "redis.call('ZREMRANGEBYSCORE', key, 0, threshold)\n" +
            "local c = redis.call('ZCARD', key)\n" +
            "return c\n";

    private static final DefaultRedisScript<Long> CLEANUP_AND_COUNT_SCRIPT;
    static {
        CLEANUP_AND_COUNT_SCRIPT = new DefaultRedisScript<>();
        CLEANUP_AND_COUNT_SCRIPT.setScriptText(CLEANUP_AND_COUNT_LUA);
        CLEANUP_AND_COUNT_SCRIPT.setResultType(Long.class);
    }

    public HeartbeatService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    /**
     * 生成业务 key。
     */
    private String buildKey(@NonNull String namespace) {
        return KEY_PREFIX + namespace;
    }

    /**
     * 客户端心跳：写入/更新 ZSET 中该用户的最后心跳时间。
     *
     * @param namespace   业务命名空间（例如 "global" 或者某个房间ID）
     * @param userId      用户唯一标识（字符串）
     * @param nowMillis   当前毫秒时间戳（可注入方便测试，也可直接传 System.currentTimeMillis()）
     */
    public void heartbeat(@NonNull String namespace, @NonNull String userId, long nowMillis) {
        String key = buildKey(namespace);
        try {
            // ZADD key nowMillis userId （若 member 已存在，则更新时间戳为最新）
            ZSetOperations<String, String> zset = redis.opsForZSet();
            zset.add(key, userId, nowMillis);

            // 可选：为 ZSET 设置一个较长的 TTL，避免长期无统计的 Key 占用内存。
            // 例如：7 天。注意：频繁心跳会自动延长 key 的存活；也可不设置 TTL。
            // redis.expire(key, Duration.ofDays(7));
        } catch (DataAccessException e) {
            log.error("写入心跳失败: namespace={}, userId={}, err={}", namespace, userId, e.toString());
            throw e;
        }
    }

    /**
     * 清理超时成员并返回在线人数（原子操作）。
     *
     * @param namespace      业务命名空间
     * @param timeoutMillis  超时时长（毫秒）。例如 60_000 表示 60 秒内有心跳视为在线。
     * @param nowMillis      当前毫秒时间戳
     * @return 清理后的在线人数
     */
    public long cleanupAndCountOnline(@NonNull String namespace, long timeoutMillis, long nowMillis) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("timeoutMillis 不能为负数");
        }
        String key = buildKey(namespace);
        long threshold = nowMillis - timeoutMillis; // 阈值：最后心跳时间 <= threshold 视为过期
        try {
            Long count = redis.execute(CLEANUP_AND_COUNT_SCRIPT, Collections.singletonList(key), String.valueOf(threshold));
            return count == null ? 0L : count;
        } catch (DataAccessException e) {
            log.error("清理并统计失败: namespace={}, timeoutMillis={}, err={}", namespace, timeoutMillis, e.toString());
            throw e;
        }
    }
}

