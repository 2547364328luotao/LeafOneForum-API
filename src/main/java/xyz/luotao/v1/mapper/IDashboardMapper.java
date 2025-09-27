package xyz.luotao.v1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.luotao.v1.entity.dto.CategoryPostCountDTO;
import xyz.luotao.v1.entity.dto.DailyCountDTO;

import java.util.List;

@Mapper
public interface IDashboardMapper {

    //查询总用户数
    @Select("SELECT COUNT(*) FROM users")
    int getTotalUsers();

    // 截至昨天的用户总数
    @Select("SELECT COUNT(*) FROM users WHERE created_at < CURDATE()")
    int getTotalUsersBeforeToday();

    // 查询文章总数
    @Select("SELECT COUNT(*) FROM posts")
    int getTotalPosts();

    // 截至昨天的文章总数
    @Select("SELECT COUNT(*) FROM posts WHERE created_at < CURDATE()")
    int getTotalPostsBeforeToday();

    // 查询评论总数（回复总数）
    @Select("SELECT COUNT(*) FROM post_replies")
    int getTotalComments();

    // 截至昨天的评论总数（回复总数）
    @Select("SELECT COUNT(*) FROM post_replies WHERE created_at < CURDATE()")
    int getTotalCommentsBeforeToday();

    // 各分类文章数量分布（包含没有文章的分类）
    @Select("""
            SELECT c.id AS categoryId,
                   c.name AS categoryName,
                   COUNT(p.id) AS postCount
            FROM categories c
            LEFT JOIN posts p ON p.category_id = c.id
            GROUP BY c.id, c.name
            ORDER BY c.id
            """)
    List<CategoryPostCountDTO> getPostCountByCategory();

    // 近7日每日新增注册人数（含今天，按日期升序）
    @Select("""
            WITH RECURSIVE days AS (
                SELECT CURDATE() - INTERVAL 6 DAY AS d
                UNION ALL
                SELECT d + INTERVAL 1 DAY FROM days WHERE d < CURDATE()
            )
            SELECT DATE_FORMAT(d, '%Y-%m-%d') AS date,
                   COUNT(u.id) AS count
            FROM days
            LEFT JOIN users u ON DATE(u.created_at) = d
            GROUP BY d
            ORDER BY d
            """)
    List<DailyCountDTO> getLast7DaysNewUsers();
}