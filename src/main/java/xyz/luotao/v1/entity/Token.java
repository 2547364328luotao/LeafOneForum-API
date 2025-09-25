package xyz.luotao.v1.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Token对象，包含JWT令牌和SessionId。
 * 用于登录成功后返回给前端，前端可用于后续认证。
 */
@Getter
@Setter
@ToString
public class Token {
    /**
     * JWT令牌字符串
     */
    private String token;
    /**
     * Session会话ID
     */
    private String sessionId;
}
