package xyz.luotao.v1.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    // 首页概览卡片数据
    private List<DashboardMetricCardDTO> overviewCards;
    // 新增：各分类文章数量分布
    private List<CategoryPostCountDTO> categoryPostDistribution;
    // 新增：近7日每日新增注册人数（含今天），按日期升序
    private List<DailyCountDTO> last7DaysNewUsers;

}
