package xyz.luotao.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.entity.dto.DashboardStatsDTO;
import xyz.luotao.v1.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // 返回仪表盘数据
    @GetMapping
    public ResponseMessage<DashboardStatsDTO> getDashboardData() {
        DashboardStatsDTO stats = dashboardService.getStats();
        return ResponseMessage.success(stats);
    }

}

