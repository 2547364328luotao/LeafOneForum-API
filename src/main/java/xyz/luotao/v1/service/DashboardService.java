package xyz.luotao.v1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luotao.v1.entity.dto.CategoryPostCountDTO;
import xyz.luotao.v1.entity.dto.DailyCountDTO;
import xyz.luotao.v1.entity.dto.DashboardStatsDTO;
import xyz.luotao.v1.mapper.IDashboardMapper;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private IDashboardMapper dashboardMapper;

    public DashboardStatsDTO getStats() {
        int users = dashboardMapper.getTotalUsers();
        int posts = dashboardMapper.getTotalPosts();
        int comments = dashboardMapper.getTotalComments();
        List<CategoryPostCountDTO> distribution = dashboardMapper.getPostCountByCategory();
        List<DailyCountDTO> last7DaysNewUsers = dashboardMapper.getLast7DaysNewUsers();

        DashboardStatsDTO dto = new DashboardStatsDTO();
        dto.setTotalUsers(users);
        dto.setTotalPosts(posts);
        dto.setTotalComments(comments);
        dto.setCategoryPostDistribution(distribution);
        dto.setLast7DaysNewUsers(last7DaysNewUsers);
        return dto;
    }
}






