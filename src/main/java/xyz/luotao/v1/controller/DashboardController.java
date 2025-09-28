package xyz.luotao.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.entity.dto.DailyCountDTO;
import xyz.luotao.v1.entity.dto.DashboardStatsDTO;
import xyz.luotao.v1.service.DashboardService;

import java.util.List;

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

    // 指定月份每日新增注册人数
    @GetMapping("/registrations")
    public ResponseMessage<List<DailyCountDTO>> getMonthlyRegistrations(@RequestParam("month") String month) {
        try {
            List<DailyCountDTO> data = dashboardService.getMonthlyRegistrations(month);
            return ResponseMessage.success(data);
        } catch (IllegalArgumentException ex) {
            return new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        }
    }

}

