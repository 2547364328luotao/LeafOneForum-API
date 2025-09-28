package xyz.luotao.v1.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.entity.dto.RepliesDto;
import xyz.luotao.v1.entity.post_replies;
import xyz.luotao.v1.mapper.IPostMapper;
import xyz.luotao.v1.mapper.IRepliesMapper;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class RepliesController {

    @Autowired
    private IPostMapper postMapper;
    @Autowired
    private IRepliesMapper repliesMapper;

    //添加回复
    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addReply(
            @Valid @RequestBody RepliesDto rep,
            HttpSession session) {
        //取出当前用户ID
        User user = (User) session.getAttribute("user");
        //检查文章是否存在
        if (postMapper.FindPostById(rep.getPostId()) == null) {
            return ResponseEntity
                    .status(404)
                    .body(new ResponseMessage(404, "文章不存在，无法添加回复", null));
        }
        boolean success = repliesMapper.addReply(rep.getPostId(), user.getId(), rep.getContent());
        if (!success) {
            return ResponseEntity
                    .status(500)
                    .body(new ResponseMessage(500, "添加回复失败", null));
        }
        return ResponseEntity.ok(ResponseMessage.success(200, "添加回复成功", null));
    }
    
    // 回复点赞（幂等）
    @PostMapping("/like")
    @Transactional
    public ResponseEntity<ResponseMessage> likeReply(
            @RequestParam @NotNull @Positive Long replyId,
            HttpSession session) {

        User user = (User) session.getAttribute("user");

        // 1. 先插入点赞记录（唯一键: reply_id + user_id）
        int inserted = repliesMapper.addReplyLikeRecord(replyId, user.getId());
        if (inserted == 0) {
            // 已存在记录 -> 返回幂等结果
            return ResponseEntity.ok(ResponseMessage.success(200, "已点赞，无需重复操作", null));
        }

        // 2. 仅在插入成功时累加点赞数
        int updated = repliesMapper.incrementReplyLikeCount(replyId);
        if (updated == 0) {
            // 触发回滚，避免只插入记录但未加计数
            throw new IllegalStateException("更新点赞数失败");
        }
        return ResponseEntity.ok(ResponseMessage.success(200, "回复点赞成功", null));
    }
    // 回复取消点赞（幂等）
    @PostMapping("/unlike")
    @Transactional
    public ResponseEntity<ResponseMessage> unlikeReply(
            @RequestParam @NotNull @Positive Long replyId,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        // 1. 先删除点赞记录
        int deleted = repliesMapper.removeReplyLikeRecord(replyId, user.getId());
        if (deleted == 0) {
            // 无记录可删 -> 返回幂等结果
            return ResponseEntity.ok(ResponseMessage.success(200, "未点赞，无需取消", null));
        }
        // 2. 仅在删除成功时递减点赞数
        int updated = repliesMapper.decrementReplyLikeCount(replyId);
        if (updated == 0) {
            // 触发回滚，避免只删记录但未减计数
            throw new IllegalStateException("更新点赞数失败");
        }
        return ResponseEntity.ok(ResponseMessage.success(200, "取消回复点赞成功", null));
    }


    //查询所有回复
    @GetMapping("/all")
    public ResponseEntity<ResponseMessage> getAllReplies() {
        List<post_replies> allReplies = repliesMapper.getAllReplies();
        return ResponseEntity.ok(ResponseMessage.success(200, "查询成功", allReplies));
    }

    // 给定文章ID查询回复数据
    @GetMapping(params = "postId")
    public ResponseEntity<ResponseMessage> getRepliesByPostId(
            @RequestParam @NotNull @Positive Long postId) {

        List<post_replies> replies = repliesMapper.getRepliesByPostId(postId);
        if (replies == null) {
            replies = Collections.emptyList();
        }
        return ResponseEntity.ok(ResponseMessage.success(200, "查询成功", replies));
    }


/**
 * @Validated：用于启动方法参数的校验功能，配合JSR-303（如Hibernate Validator）实现参数自动校验。常用于Controller类或方法上。
 * @PathVariable：用于将URL路径中的变量绑定到方法参数。例如，/api/replies/{postId}中的postId会自动赋值给方法参数。
 * 其他类似注解还有：
 * @RequestParam：绑定请求参数（如URL中的?key=value）到方法参数。
 * @RequestBody：将请求体中的JSON/XML等内容绑定到方法参数（通常是对象）。
 * @RequestHeader：绑定请求头中的值到方法参数。
 * @ModelAttribute：用于绑定表单数据到对象，并可用于数据预处理。
 */

}
