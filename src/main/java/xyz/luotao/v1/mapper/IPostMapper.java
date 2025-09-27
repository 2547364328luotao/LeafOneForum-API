package xyz.luotao.v1.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.ibatis.annotations.*;
import xyz.luotao.v1.entity.Post;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.entity.dto.PostDto;

@Mapper
public interface IPostMapper {

    //获取最新文章
    @Select("SELECT p.*, u.nickname AS author_nickname " +
            "FROM posts p LEFT JOIN users u ON p.author_id = u.id " +
            "ORDER BY p.created_at DESC LIMIT #{limit}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "title", property = "title"),
            @Result(column = "cover_url", property = "coverUrl"),
            @Result(column = "synopsis", property = "synopsis"),
            @Result(column = "content", property = "content"),
            @Result(column = "status", property = "status"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(column = "author_nickname", property = "authorNickname")
    })
    java.util.List<Post> selectLatestPosts(@Param("limit") @NotNull @Positive int limit);

    // 分页：所有文章带上作者信息
    @Select("SELECT p.*, u.nickname AS author_nickname " +
            "FROM posts p LEFT JOIN users u ON p.author_id = u.id " +
            "ORDER BY p.created_at DESC")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "title", property = "title"),
            @Result(column = "cover_url", property = "coverUrl"),
            @Result(column = "synopsis", property = "synopsis"),
            @Result(column = "content", property = "content"),
            @Result(column = "status", property = "status"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(column = "author_nickname", property = "authorNickname")
    })
    IPage<Post> selectPostPage(Page<Post> page);

    //

    //所有文章带上作者信息
    @Select("SELECT p.*, u.nickname AS author_nickname FROM posts p LEFT JOIN users u ON p.author_id = u.id ORDER BY p.created_at DESC")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "status", property = "status"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(column = "author_nickname", property = "authorNickname")
    })
    public java.util.List<Post> FindAllPosts();


    //根据文章ID查询文章
    @Select("SELECT * FROM posts WHERE id = #{id}")
    @Results(
            {
                    @Result(column = "id", property = "id"),
                    @Result(column = "category_id", property = "categoryId"),
                    @Result(column = "author_id", property = "authorId"),
                    @Result(column = "title", property = "title"),
                    @Result(column = "content", property = "content"),
                    @Result(column = "status", property = "status"),
                    @Result(column = "created_at", property = "createdAt"),
                    @Result(column = "updated_at", property = "updatedAt"),
                    @Result(column = "author_id",property = "user",javaType = User.class,
                            one = @One(select = "xyz.luotao.v1.mapper.IUserMapper.FindById"))
            }
    )
    public Post FindPostById(Long id);
    //根据作者ID查询文章
    @Select("SELECT * FROM posts WHERE author_id = #{authorId} ORDER BY created_at DESC")
    public java.util.List<Post> FindPostsByAuthorId(Long authorId);

    //根据分类ID查询文章
    @Select("SELECT p.*, u.nickname AS author_nickname FROM posts p LEFT JOIN users u ON p.author_id = u.id WHERE p.category_id = #{categoryId} ORDER BY p.created_at DESC")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "status", property = "status"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(column = "author_nickname", property = "authorNickname")
    })
    public java.util.List<Post> FindPostsByCategoryId(Long categoryId);

    //分页查询分类文章
    @Select("SELECT p.*, u.nickname AS author_nickname " +
            "FROM posts p LEFT JOIN users u ON p.author_id = u.id " +
            "WHERE p.category_id = #{categoryId} " +
            "ORDER BY p.created_at DESC")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "title", property = "title"),
            @Result(column = "cover_url", property = "coverUrl"),
            @Result(column = "synopsis", property = "synopsis"),
            @Result(column = "content", property = "content"),
            @Result(column = "status", property = "status"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(column = "author_nickname", property = "authorNickname")
    })
    IPage<Post> selectPostsByCategoryPage(Page<Post> page, @Param("categoryId") Long categoryId);

    //根据文章标题模糊查询文章列表
    @Select("SELECT p.*, u.nickname AS author_nickname FROM posts p LEFT JOIN users u ON p.author_id = u.id WHERE p.title LIKE CONCAT('%', #{title}, '%') ORDER BY p.created_at DESC")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "status", property = "status"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(column = "author_nickname", property = "authorNickname")
    })
    public java.util.List<Post> FindPostsByTitle(String title);

    //给文章点赞
    @Update("UPDATE posts SET like_count = like_count + 1 WHERE id = #{id}")
    public boolean LikePost(Long id);

    //添加文章
    @Insert("INSERT INTO posts (category_id, author_id, title, cover_url , synopsis, content, status, created_at, updated_at) " +
            "VALUES (#{post.categoryId}, #{authorId}, #{post.title}, #{post.coverUrl}, #{post.synopsis}, #{post.content}, 'published', NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "post.id")
    boolean AddPost(PostDto post, Long authorId);

    //增加文章浏览量
    @Update("UPDATE posts SET view_count = view_count + 1 WHERE id = #{postId}")
    public boolean incrementViewCount(Long postId);

}
