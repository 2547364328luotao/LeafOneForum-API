package xyz.luotao.v1.entity.dto;

import lombok.Data;

/**
 * 心跳上报请求 DTO
 */
@Data
public class HeartbeatRequest {
    /** 业务命名空间：不传默认 global */
    private String namespace;
    /** 用户唯一标识 */
    private String userId;
    /** 可选：当前毫秒时间戳（一般不传，由服务端生成） */
    private Long nowMillis;
}

