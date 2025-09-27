package xyz.luotao.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.service.HeartbeatService;
import xyz.luotao.v1.entity.dto.HeartbeatRequest;
import xyz.luotao.v1.entity.dto.HeartbeatResponse;
import xyz.luotao.v1.entity.dto.CountResponse;

/**
 * Redis ZSET + 心跳 的参考实现（超详细注释版）。
 *
 * - 为什么用 ZSET?
 *   使用 ZSET 的 score 存储“最后一次心跳的毫秒时间戳”，member 存储用户ID。
 *   这样同一用户重复心跳就是 ZADD 覆盖分数，天然去重；清理过期就是按分数范围删。
 *
 * - 为什么能支持多实例横向扩展?
 *   所有应用实例写入同一个 Redis、同一个 ZSET key（按 namespace 区分业务/房间）。
 *   Redis 是集中式的，多个实例同时写也没问题；统计时用 Lua 脚本原子地“清理 + 计数”，避免并发误差。
 *
 * - 客户端怎么用?
 *   1) 定时（比如每 15~30 秒）调用 /public/online/heartbeat 上报心跳。
 *   2) 需要展示在线人数时，调用 /public/online/count?timeoutSec=60（或更大），即可得到在线数。
 */
@RestController
@RequestMapping("/public/online")
public class OnlineController {

    @Autowired
    private HeartbeatService heartbeatService;

    /**
     * 心跳上报接口。
     *
     * 使用说明：
     * - 建议客户端每 15~30 秒调用一次。
     * - 如果你的系统已有登录/鉴权，可以用登录用户ID；演示环境下也可用随机UUID。
     * - namespace 可用于区分业务/房间/频道，不传则默认 "global"。
     */
    @PostMapping("/heartbeat")
    public ResponseEntity<ResponseMessage<?>> heartbeat(@RequestBody HeartbeatRequest req) {
        String namespace = StringUtils.hasText(req.getNamespace()) ? req.getNamespace() : "global";
        if (!StringUtils.hasText(req.getUserId())) {
            return ResponseEntity.badRequest().body(new ResponseMessage<>(400, "userId 不能为空", null));
        }
        long now = (req.getNowMillis() != null && req.getNowMillis() > 0) ? req.getNowMillis() : System.currentTimeMillis();

        // 核心：ZADD key score(member=用户ID, score=最后心跳时间戳)
        heartbeatService.heartbeat(namespace, req.getUserId(), now);

        HeartbeatResponse body = new HeartbeatResponse();
        body.setNamespace(namespace);
        body.setUserId(req.getUserId());
        body.setNowMillis(now);
        body.setRecommendNextHeartbeatSec(30); // 建议下次心跳间隔（可按需调整）
        return ResponseEntity.ok(ResponseMessage.success(body));
    }

    /**
     * 在线人数统计接口：原子地“清理超时成员 + 统计在线人数”。
     *
     * 参数：
     * - namespace：业务命名空间，不传默认 "global"。
     * - timeoutSec：超时时长（秒）。多少秒内有心跳视为在线，默认 60 秒。
     */
    @GetMapping("/count")
    public ResponseEntity<ResponseMessage<?>> count(
            @RequestParam(value = "namespace", required = false) String namespace,
            @RequestParam(value = "timeoutSec", required = false) Integer timeoutSec
    ) {
        String ns = StringUtils.hasText(namespace) ? namespace : "global";
        int ts = (timeoutSec != null && timeoutSec > 0) ? timeoutSec : 60;
        long now = System.currentTimeMillis();

        long count = heartbeatService.cleanupAndCountOnline(ns, ts * 1000L, now);

        CountResponse body = new CountResponse();
        body.setNamespace(ns);
        body.setTimeoutSec(ts);
        body.setNowMillis(now);
        body.setOnlineCount(count);
        return ResponseEntity.ok(ResponseMessage.success(body));
    }
}
