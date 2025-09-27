package xyz.luotao.v1.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardMetricCardDTO {
    private String title;
    private String value;
    private String change;
    private String changeType;
    private String icon;
    private String description;
}
