package xyz.luotao.v1.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostLikeMapper {

    //插入文章点赞记录
    @Insert("INSERT INTO post_likes(post_id, user_id) VALUES(#{postId}, #{userId})")
    public boolean AddPostLike(Long postId, Long userId);

}
