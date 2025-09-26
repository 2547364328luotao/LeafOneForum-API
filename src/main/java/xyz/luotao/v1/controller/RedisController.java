package xyz.luotao.v1.controller;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        try {
            String pong = stringRedisTemplate.execute(RedisConnection::ping);
            if (pong == null) {
                return ResponseEntity.internalServerError().body("PING 无响应");
            }
            return ResponseEntity.ok(pong);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("连接失败: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
