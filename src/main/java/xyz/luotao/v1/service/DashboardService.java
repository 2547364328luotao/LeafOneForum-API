package xyz.luotao.v1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luotao.v1.entity.dto.CategoryPostCountDTO;
import xyz.luotao.v1.entity.dto.DailyCountDTO;
import xyz.luotao.v1.entity.dto.DashboardMetricCardDTO;
import xyz.luotao.v1.entity.dto.DashboardStatsDTO;
import xyz.luotao.v1.mapper.IDashboardMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class DashboardService {

    @Autowired
    private IDashboardMapper dashboardMapper;

    @Autowired(required = false)
    private HeartbeatService heartbeatService;

    private static final String DEFAULT_ONLINE_NAMESPACE = "global";
    private static final long ONLINE_TIMEOUT_MILLIS = 60_000L;
    private static final List<DashboardMetricCardDTO> DEFAULT_CARD_TEMPLATES = List.of(
            new DashboardMetricCardDTO("注册用户", "待定", "待定", "increase", "<Users className=\"h-4 w-4\" />", "总用户数"),
            new DashboardMetricCardDTO("在线用户", "待定", "待定", "increase", "<UserCheck className=\"h-4 w-4\" />", "当前在线"),
            new DashboardMetricCardDTO("论坛文章", "待定", "待定", "increase", "<FileText className=\"h-4 w-4\" />", "已发布文章"),
            new DashboardMetricCardDTO("评论互动", "待定", "待定", "increase", "<MessageSquare className=\"h-4 w-4\" />", "用户评论")
    );

    public DashboardStatsDTO getStats() {
        int users = dashboardMapper.getTotalUsers();
        int posts = dashboardMapper.getTotalPosts();
        int comments = dashboardMapper.getTotalComments();
        int usersYesterday = dashboardMapper.getTotalUsersBeforeToday();
        int postsYesterday = dashboardMapper.getTotalPostsBeforeToday();
        int commentsYesterday = dashboardMapper.getTotalCommentsBeforeToday();
        List<CategoryPostCountDTO> distribution = dashboardMapper.getPostCountByCategory();
        List<DailyCountDTO> last7DaysNewUsers = dashboardMapper.getLast7DaysNewUsers();
        long online = fetchCurrentOnlineCount();
        long onlineYesterday = fetchYesterdayOnlineCount(online);

        DashboardStatsDTO dto = new DashboardStatsDTO();
        dto.setCategoryPostDistribution(distribution);
        dto.setLast7DaysNewUsers(last7DaysNewUsers);
        dto.setOverviewCards(buildOverviewCards(users, usersYesterday, online, onlineYesterday, posts, postsYesterday, comments, commentsYesterday));
        return dto;
    }

    private List<DashboardMetricCardDTO> buildOverviewCards(int users, int usersYesterday,
                                                            long online, long onlineYesterday,
                                                            int posts, int postsYesterday,
                                                            int comments, int commentsYesterday) {
        List<DashboardMetricCardDTO> cards = new ArrayList<>(DEFAULT_CARD_TEMPLATES.size());
        cards.add(buildCard(DEFAULT_CARD_TEMPLATES.get(0), users, usersYesterday));
        cards.add(buildCard(DEFAULT_CARD_TEMPLATES.get(1), online, onlineYesterday));
        cards.add(buildCard(DEFAULT_CARD_TEMPLATES.get(2), posts, postsYesterday));
        cards.add(buildCard(DEFAULT_CARD_TEMPLATES.get(3), comments, commentsYesterday));
        return cards;
    }

    private DashboardMetricCardDTO buildCard(DashboardMetricCardDTO template, long current, long previous) {
        double changePercent = calculatePercentChange(current, previous);
        String changeType = changePercent < 0 ? "decrease" : "increase";
        return new DashboardMetricCardDTO(
                template.getTitle(),
                formatNumber(current),
                formatPercent(changePercent),
                changeType,
                template.getIcon(),
                template.getDescription()
        );
    }

    private double calculatePercentChange(long current, long previous) {
        if (previous <= 0) {
            return current > 0 ? 100.0 : 0.0;
        }
        return ((double) current - previous) * 100.0 / previous;
    }

    private String formatPercent(double value) {
        return String.format(Locale.US, "%+,.1f%%", value);
    }

    private String formatNumber(long value) {
        return String.format(Locale.US, "%,d", value);
    }

    private long fetchCurrentOnlineCount() {
        if (heartbeatService == null) {
            return 0L;
        }
        long now = System.currentTimeMillis();
        return heartbeatService.cleanupAndCountOnline(DEFAULT_ONLINE_NAMESPACE, ONLINE_TIMEOUT_MILLIS, now);
    }

    private long fetchYesterdayOnlineCount(long fallback) {
        // 暂无历史在线数据持久化，返回当前值以保持涨幅为 0。
        return fallback;
    }
}






