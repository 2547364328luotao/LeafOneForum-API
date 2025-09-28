package xyz.luotao.v1.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostLikeMapper {

    //插入文章点赞记录
    @Insert("INSERT INTO post_likes(post_id, user_id) VALUES(#{postId}, #{userId})")
    public boolean AddPostLike(Long postId, Long userId);

    //并在post_likes表中删除用户点赞信息
    @Delete("DELETE FROM post_likes WHERE post_id = #{postId} AND user_id = #{userId}")
    boolean RemovePostLike(@Param("postId") Long postId, @Param("userId") Long userId);

}
