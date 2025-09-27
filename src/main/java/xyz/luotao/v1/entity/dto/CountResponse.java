package xyz.luotao.v1.entity.dto;

import lombok.Data;

/**
 * 在线人数统计响应 DTO
 */
@Data
public class CountResponse {
    private String namespace;
    private int timeoutSec;
    private long nowMillis;
    private long onlineCount;
}

