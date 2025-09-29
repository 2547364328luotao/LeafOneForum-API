package xyz.luotao.v1.controller;

//待升级清单： Spring Security

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.entity.Post;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.entity.dto.PostDto;
import xyz.luotao.v1.mapper.IPostMapper;
import xyz.luotao.v1.mapper.IUserRoleMapper;
import xyz.luotao.v1.mapper.IPostLikeMapper;
import xyz.luotao.v1.service.RolesService;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostCotroller {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IPostMapper postMapper;
    @Autowired
    private IPostLikeMapper IPostLikeMapper;
    @Autowired
    private IUserRoleMapper userRoleMapper;
    @Autowired
    private RolesService rolesService;

    //编写文章
    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createPost(@RequestBody PostDto postDao, HttpSession session){
        User user = (User)session.getAttribute("user");
        ResponseEntity<ResponseMessage> deny = rolesService.enforcePostPermission(user);
        if(deny != null){
            return deny;
        }

        Long authorId = user.getId();
        boolean success = postMapper.AddPost(postDao,authorId);
        if (!success) {
            return ResponseEntity
                    .status(500)
                    .body(new ResponseMessage(500, "发布文章失败", null));
        }
        log.info("成功发布文章：{}", postDao);
        return ResponseEntity.ok(ResponseMessage.success(200,"发布文章成功",postDao));
    }

    //获取最新文章
    @GetMapping("/latest")
    public ResponseEntity<ResponseMessage> getLatestPosts(@RequestParam(defaultValue = "5") int limit){
        List<Post> posts = postMapper.selectLatestPosts(limit);
        log.info("获取最新文章成功：{}", posts);
        return ResponseEntity.ok(ResponseMessage.success(200,"最新文章返回成功",posts));
    }

    //分页查询所有文章
    @GetMapping("/findByPage")
    public ResponseEntity<ResponseMessage> getPostsByPage(@RequestParam(defaultValue = "1") long pageNo){
        //设置起始页为1，页面大小为10
        Page<Post> page = new Page<>(pageNo, 9);
        IPage iPage = postMapper.selectPostPage(page);
        log.info("分页查询所有文章成功：{}", iPage);
        return ResponseEntity.ok(ResponseMessage.success(200,"文章列表返回成功",iPage));
    }

    //分页查询分类文章
    @GetMapping("/findByCategoryPage")
    public ResponseEntity<ResponseMessage> getPostsByCategoryPage(@RequestParam Long categoryId, @RequestParam(defaultValue = "1") long pageNo){
        //设置起始页为1，页面大小为10
        Page<Post> page = new Page<>(pageNo, 6);
        IPage iPage = postMapper.selectPostsByCategoryPage(page, categoryId);
        log.info("分页查询分类文章成功：{}", iPage);
        return ResponseEntity.ok(ResponseMessage.success(200,"文章列表返回成功",iPage));
    }

    //根据文章ID查询文章
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getPost(@PathVariable Long id){
        Post post = postMapper.FindPostById(id);
        log.info("根据ID查询文章成功：{}", post);
        return ResponseEntity.ok(ResponseMessage.success(200,"文章返回成功",post));
    }

    //根据文章分类ID查询文章列表
    @GetMapping("/category")
    public ResponseEntity<ResponseMessage> getPostsByCategoryId(@Validated Long page){
        List<Post> posts = postMapper.FindPostsByCategoryId(page);
        log.info("根据分类ID查询文章列表成功：{}", posts);
        return ResponseEntity.ok(ResponseMessage.success(200,"文章列表返回成功",posts));
    }

    //根据文章标题模糊查询文章列表
    @GetMapping("/search")
    public ResponseEntity<ResponseMessage> getPostsByTitle(@RequestParam String title){
        List<Post> posts = postMapper.FindPostsByTitle(title);
        log.info("根据文章标题模糊查询文章列表成功：{}", posts);
        return ResponseEntity.ok(ResponseMessage.success(200,"文章列表返回成功",posts));
    }

    //给文章点赞
    @PostMapping("/like")
    public ResponseEntity<ResponseMessage> likePost(@RequestParam Long postId, HttpSession session){
        //打印SESSIONID
        System.out.println(session.getId());
        User user = (User)session.getAttribute("user");
        try{
            boolean success1 = postMapper.LikePost(postId);
            boolean success2 = IPostLikeMapper.AddPostLike(postId, user.getId());
            if (!success1 || !success2) {
                return ResponseEntity
                        .status(500)
                        .body(new ResponseMessage(500, "点赞文章失败", null));
            }
            log.info("成功点赞文章，文章ID：{}", postId);
            return ResponseEntity.ok(ResponseMessage.success(200,"点赞文章成功",null));
        }catch (org.springframework.dao.DuplicateKeyException e){
            return ResponseEntity.ok(ResponseMessage.success(200, "已点赞成功，请勿重复点击", null));
        }catch (org.springframework.dao.DataIntegrityViolationException e){
            // 捕获由于外键约束（文章不存在）导致的异常
            log.warn("点赞文章失败，文章不存在，postId={}", postId, e);
            return ResponseEntity.ok(ResponseMessage.success(200, "暂无此文章ID", null));
        }

    }

    //取消文章点赞
    @PostMapping("/unlike")
    public ResponseEntity<ResponseMessage> unlikePost(@RequestParam Long postId, HttpSession session){
        User user = (User)session.getAttribute("user");
        boolean success1 = postMapper.UnlikePost(postId);
        boolean success2 = IPostLikeMapper.RemovePostLike(postId, user.getId());
        if (!success1 || !success2) {
            return ResponseEntity
                    .status(500)
                    .body(new ResponseMessage(500, "取消点赞文章失败", null));
        }
        log.info("成功取消点赞文章，文章ID：{}", postId);
        return ResponseEntity.ok(ResponseMessage.success(200,"取消点赞文章成功",null));
    }

    //文章阅读量增加
    @PostMapping("/view/{postId}")
    public ResponseEntity<ResponseMessage> viewPost(@PathVariable Long postId, HttpSession session){
        boolean view_success = postMapper.incrementViewCount(postId);
        if (!view_success) {
            return ResponseEntity
                    .status(500)
                    .body(new ResponseMessage(500, "文章阅读量增加失败", null));
        }
        log.info("成功增加文章阅读量，文章ID：{}", postId);
        return ResponseEntity.ok(ResponseMessage.success(200,"文章阅读量增加成功",null));
    }
}
