package xyz.luotao.v1.mapper;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.ibatis.annotations.*;
import xyz.luotao.v1.entity.post_replies;

import java.util.List;

@Mapper
public interface IRepliesMapper {

    // 添加回复
    @Insert("INSERT INTO post_replies (post_id, user_id, content, created_at) " +
            "VALUES (#{postId}, #{userId}, #{content}, NOW())")
    boolean addReply(Long postId, Long userId, String content);

    //查询所有回复
    @Select("SELECT * FROM post_replies")
    List<post_replies> getAllReplies();

    //给定文章ID查询回复数据
    @Select("SELECT * FROM post_replies WHERE post_id = #{postId} ORDER BY created_at ASC")
    List<post_replies> getRepliesByPostId(Long postId);

    //回复点赞
    @Update("UPDATE post_replies SET like_count = like_count + 1 WHERE id = #{replyId}")
    int incrementReplyLikeCount(@Param("replyId") Long replyId);

    //添加回复点赞记录
    @Insert("INSERT IGNORE INTO reply_likes (reply_id, user_id, created_at) " +
            "VALUES (#{replyId}, #{userId}, NOW())")
    int addReplyLikeRecord(@Param("replyId") Long replyId, @Param("userId") Long userId);

    //取消回复点赞
    @Delete("DELETE FROM reply_likes WHERE reply_id = #{replyId} AND user_id = #{userId }")
    int removeReplyLikeRecord(@Param("replyId") Long replyId, @Param("userId") Long userId);

    // 回复取消点赞
    @Update("UPDATE post_replies SET like_count = like_count - 1 WHERE id = #{replyId} AND like_count > 0")
    int decrementReplyLikeCount(@Param("replyId") Long replyId);

    // 根据ID查询回复
    @Select("SELECT * FROM post_replies WHERE id = #{replyId}")
    post_replies findReplyById(@Param("replyId") Long replyId);

}
