package xyz.luotao.v1.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 文章回复表
* @TableName post_replies
*/
public class post_replies implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 文章ID
    */
    @NotNull(message="[文章ID]不能为空")
    @ApiModelProperty("文章ID")
    private Long postId;
    /**
    * 回复者用户ID
    */
    @NotNull(message="[回复者用户ID]不能为空")
    @ApiModelProperty("回复者用户ID")
    private Long userId;
    /**
    * 父级回复ID（楼中楼，可空）
    */
    @ApiModelProperty("父级回复ID（楼中楼，可空）")
    private Long parentReplyId;
    /**
    * 回复内容
    */
    @NotBlank(message="[回复内容]不能为空")
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("回复内容")
    @Length(max= -1,message="编码长度不能超过-1")
    private String content;
    /**
    * 点赞数（冗余计数）
    */
    @ApiModelProperty("点赞数（冗余计数）")
    private Integer likeCount;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date createdAt;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date updatedAt;

    /**
    * 
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 文章ID
    */
    public void setPostId(Long postId){
    this.postId = postId;
    }

    /**
    * 回复者用户ID
    */
    public void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 父级回复ID（楼中楼，可空）
    */
    public void setParentReplyId(Long parentReplyId){
    this.parentReplyId = parentReplyId;
    }

    /**
    * 回复内容
    */
    public void setContent(String content){
    this.content = content;
    }

    /**
    * 点赞数（冗余计数）
    */
    public void setLikeCount(Integer likeCount){
    this.likeCount = likeCount;
    }

    /**
    * 
    */
    public void setCreatedAt(Date createdAt){
    this.createdAt = createdAt;
    }

    /**
    * 
    */
    public void setUpdatedAt(Date updatedAt){
    this.updatedAt = updatedAt;
    }


    /**
    * 
    */
    public Long getId(){
    return this.id;
    }

    /**
    * 文章ID
    */
    public Long getPostId(){
    return this.postId;
    }

    /**
    * 回复者用户ID
    */
    public Long getUserId(){
    return this.userId;
    }

    /**
    * 父级回复ID（楼中楼，可空）
    */
    public Long getParentReplyId(){
    return this.parentReplyId;
    }

    /**
    * 回复内容
    */
    public String getContent(){
    return this.content;
    }

    /**
    * 点赞数（冗余计数）
    */
    public Integer getLikeCount(){
    return this.likeCount;
    }

    /**
    * 
    */
    public Date getCreatedAt(){
    return this.createdAt;
    }

    /**
    * 
    */
    public Date getUpdatedAt(){
    return this.updatedAt;
    }

}
