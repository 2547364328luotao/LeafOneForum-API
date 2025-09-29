package xyz.luotao.v1.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IPostLikeMapper {

    //插入文章点赞记录
    @Insert("INSERT INTO post_likes(post_id, user_id) VALUES(#{postId}, #{userId})")
    public boolean AddPostLike(Long postId, Long userId);

    //并在post_likes表中删除用户点赞信息
    @Delete("DELETE FROM post_likes WHERE post_id = #{postId} AND user_id = #{userId}")
    boolean RemovePostLike(@Param("postId") Long postId, @Param("userId") Long userId);

    //根据用户ID查询用户点赞的文章ID
    @Select("SELECT post_id FROM post_likes WHERE user_id = #{userId}")
    List<Long> FindLikedPostIdsByUserId(Long userId);

}
