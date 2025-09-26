package xyz.luotao.v1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class RedisConnectionTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void ping_shouldReturnPONG() {
        String pong = stringRedisTemplate.execute(RedisConnection::ping);
        System.out.println("Redis PING => " + pong);
        Assertions.assertEquals("PONG", pong, "期望返回 PONG");
    }
}

