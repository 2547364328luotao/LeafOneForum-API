package xyz.luotao.v1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 文章主表
* @TableName posts
*/
@TableName(value ="posts")
public class Post implements Serializable {

    /**
    * 文章ID
    */
    @NotNull(message="[文章ID]不能为空")
    @ApiModelProperty("文章ID")
    private Long id;
    /**
    * 分类ID（可空）
    */
    @ApiModelProperty("分类ID（可空）")
    private Long categoryId;
    /**
    * 作者用户ID（FK users.id）
    */
    @NotNull(message="[作者用户ID（FK users.id）]不能为空")
    @ApiModelProperty("作者用户ID（FK users.id）")
    private Long authorId;
    /**
    * 标题
    */
    @NotBlank(message="[标题]不能为空")
    @Size(max= 200,message="编码长度不能超过200")
    @ApiModelProperty("标题")
    @Length(max= 200,message="编码长度不能超过200")
    private String title;

    /**
     * 封面图片URL
     */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("封面图片URL")
    @Length(max= 255,message="编码长度不能超过255")
    private String coverUrl;

    /**
     * 文章简介
     */
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("文章简介")
    @Length(max= 500,message="编码长度不能超过500")
    private String synopsis;

    /**
    * 正文内容（可存Markdown）
    */
    @NotBlank(message="[正文内容（可存Markdown）]不能为空")
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("正文内容（可存Markdown）")
    @Length(max= -1,message="编码长度不能超过-1")
    private String content;
    /**
    * 状态：草稿/已发布/归档
    */
    @NotNull(message="[状态：草稿/已发布/归档]不能为空")
    @ApiModelProperty("状态：草稿/已发布/归档")
    private Object status;
    /**
    * 是否置顶
    */
    @NotNull(message="[是否置顶]不能为空")
    @ApiModelProperty("是否置顶")
    private Integer isPinned;
    /**
    * 是否锁帖（禁止新回复）
    */
    @NotNull(message="[是否锁帖（禁止新回复）]不能为空")
    @ApiModelProperty("是否锁帖（禁止新回复）")
    private Integer isLocked;
    /**
    * 浏览次数（PV）
    */
    @NotNull(message="[浏览次数（PV）]不能为空")
    @ApiModelProperty("浏览次数（PV）")
    private Long viewCount;
    /**
    * 回复数（冗余计数）
    */
    @NotNull(message="[回复数（冗余计数）]不能为空")
    @ApiModelProperty("回复数（冗余计数）")
    private Integer replyCount;
    /**
    * 点赞数（冗余计数）
    */
    @NotNull(message="[点赞数（冗余计数）]不能为空")
    @ApiModelProperty("点赞数（冗余计数）")
    private Integer likeCount;
    /**
    * 最后回复时间
    */
    @ApiModelProperty("最后回复时间")
    private Date lastReplyAt;
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
     * 作者信息集
     */
    @TableField(exist = false)
    private User user;

    /**
     * 作者昵称
     */
    private String authorNickname;

    public String getAuthorNickname() {
        return authorNickname;
    }

    public void setAuthorNickname(String authorNickname) {
        this.authorNickname = authorNickname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
    * 文章ID
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 分类ID（可空）
    */
    public void setCategoryId(Long categoryId){
    this.categoryId = categoryId;
    }

    /**
    * 作者用户ID（FK users.id）
    */
    public void setAuthorId(Long authorId){
    this.authorId = authorId;
    }

    /**
    * 标题
    */
    public void setTitle(String title){
    this.title = title;
    }

    /**
    * 正文内容（可存Markdown）
    */
    public void setContent(String content){
    this.content = content;
    }

    /**
    * 状态：草稿/已发布/归档
    */
    public void setStatus(Object status){
    this.status = status;
    }

    /**
    * 是否置顶
    */
    public void setIsPinned(Integer isPinned){
    this.isPinned = isPinned;
    }

    /**
    * 是否锁帖（禁止新回复）
    */
    public void setIsLocked(Integer isLocked){
    this.isLocked = isLocked;
    }

    /**
    * 浏览次数（PV）
    */
    public void setViewCount(Long viewCount){
    this.viewCount = viewCount;
    }

    /**
    * 回复数（冗余计数）
    */
    public void setReplyCount(Integer replyCount){
    this.replyCount = replyCount;
    }

    /**
    * 点赞数（冗余计数）
    */
    public void setLikeCount(Integer likeCount){
    this.likeCount = likeCount;
    }

    /**
    * 最后回复时间
    */
    public void setLastReplyAt(Date lastReplyAt){
    this.lastReplyAt = lastReplyAt;
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
    * 文章ID
    */
    public Long getId(){
    return this.id;
    }

    /**
    * 分类ID（可空）
    */
    public Long getCategoryId(){
    return this.categoryId;
    }

    /**
    * 作者用户ID（FK users.id）
    */
    public Long getAuthorId(){
    return this.authorId;
    }

    /**
    * 标题
    */
    public String getTitle(){
    return this.title;
    }

    /**
    * 正文内容（可存Markdown）
    */
    public String getContent(){
    return this.content;
    }

    /**
    * 状态：草稿/已发布/归档
    */
    public Object getStatus(){
    return this.status;
    }

    /**
    * 是否置顶
    */
    public Integer getIsPinned(){
    return this.isPinned;
    }

    /**
    * 是否锁帖（禁止新回复）
    */
    public Integer getIsLocked(){
    return this.isLocked;
    }

    /**
    * 浏览次数（PV）
    */
    public Long getViewCount(){
    return this.viewCount;
    }

    /**
    * 回复数（冗余计数）
    */
    public Integer getReplyCount(){
    return this.replyCount;
    }

    /**
    * 点赞数（冗余计数）
    */
    public Integer getLikeCount(){
    return this.likeCount;
    }

    /**
    * 最后回复时间
    */
    public Date getLastReplyAt(){
    return this.lastReplyAt;
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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
