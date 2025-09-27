package xyz.luotao.v1.entity.dto;

import lombok.Data;

/**
 * 心跳上报响应 DTO
 */
@Data
public class HeartbeatResponse {
    private String namespace;
    private String userId;
    private long nowMillis;
    private int recommendNextHeartbeatSec;
}

