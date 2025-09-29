package xyz.luotao.v1.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
* 文章点赞关联表
* @TableName PostLikes
*/
@TableName(value ="post_likes")
public class PostLikes implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long postId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long userId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date createdAt;

    /**
    * 
    */
    public void setPostId(Long postId){
    this.postId = postId;
    }

    /**
    * 
    */
    public void setUserId(Long userId){
    this.userId = userId;
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
    public Long getPostId(){
    return this.postId;
    }

    /**
    * 
    */
    public Long getUserId(){
    return this.userId;
    }

    /**
    * 
    */
    public Date getCreatedAt(){
    return this.createdAt;
    }

}
