package xyz.luotao.v1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
/**
* 叶一论坛-用户表
* @TableName users
*/
@TableName("users")
@Data
public class User implements Serializable {


    /**
    * 用户ID
    */
    //@NotNull(message="[用户ID]不能为空")
    @Id
    @ApiModelProperty("用户ID")
    private Long id;
    /**
    * 昵称
    */
    //@NotBlank(message="[昵称]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("昵称")
    @Length(max= 50,message="编码长度不能超过50")
    private String nickname;
    /**
    * 邮箱（唯一）
    */
    //@NotBlank(message="[邮箱（唯一）]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("邮箱（唯一）")
    @Length(max= 255,message="编码长度不能超过255")
    @Email
    private String email;
    /**
    * 密码哈希（如 bcrypt）
    */
    //@NotBlank(message="[密码哈希（如 bcrypt）]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("密码哈希（如 bcrypt）")
    @Length(max= 255,message="编码长度不能超过255")
    private String passwordHash;
    /**
    * 房间号，例如：南一548
    */
    //@NotBlank(message="[房间号，例如：南一548]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("房间号，例如：南一548")
    @Length(max= 50,message="编码长度不能超过50")
    private String roomNumber;
    /**
    * 房间电表密码（默认值）
    */
    //@NotBlank(message="[房间电表密码（默认值）]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("房间电表密码（默认值）")
    @Length(max= 100,message="编码长度不能超过100")
    private String roomMeterPassword;
    /**
    * 账号创建时间
    */
    //@NotNull(message="[账号创建时间]不能为空")
    @ApiModelProperty("账号创建时间")
    private Date createdAt;
    /**
     * 文章列表
     */
    @TableField(exist = false)
    private List<Post> posts;
    /**
     * 头像URL
     */
    @ApiModelProperty("头像URL")
    private String avatarUrl;

    /**
     * 用户点赞的文章ID列表
     */
    @TableField(exist = false)
    private List<Long> likedPostIds;


}
