package xyz.luotao.v1.entity;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 角色表
* @TableName roles
*/
public class roles implements Serializable {

    /**
    * 
    */
    //@NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 系统唯一标识，如 admin, organizer, moderator, user, banned
    */
    //@NotBlank(message="[系统唯一标识，如 admin, organizer, moderator, user, banned]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("系统唯一标识，如 admin, organizer, moderator, user, banned")
    @Length(max= 50,message="编码长度不能超过50")
    private String name;
    /**
    * 展示名称
    */
    //@NotBlank(message="[展示名称]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("展示名称")
    @Length(max= 100,message="编码长度不能超过100")
    private String displayName;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String description;
    /**
    * 是否系统内置角色
    */
    //@NotNull(message="[是否系统内置角色]不能为空")
    @ApiModelProperty("是否系统内置角色")
    private Integer isSystem;
    /**
    * 
    */
    //@NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date createdAt;

    /**
    * 
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 系统唯一标识，如 admin, organizer, moderator, user, banned
    */
    public void setName(String name){
    this.name = name;
    }

    /**
    * 展示名称
    */
    public void setDisplayName(String displayName){
    this.displayName = displayName;
    }

    /**
    * 
    */
    public void setDescription(String description){
    this.description = description;
    }

    /**
    * 是否系统内置角色
    */
    public void setIsSystem(Integer isSystem){
    this.isSystem = isSystem;
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
    public Long getId(){
    return this.id;
    }

    /**
    * 系统唯一标识，如 admin, organizer, moderator, user, banned
    */
    public String getName(){
    return this.name;
    }

    /**
    * 展示名称
    */
    public String getDisplayName(){
    return this.displayName;
    }

    /**
    * 
    */
    public String getDescription(){
    return this.description;
    }

    /**
    * 是否系统内置角色
    */
    public Integer getIsSystem(){
    return this.isSystem;
    }

    /**
    * 
    */
    public Date getCreatedAt(){
    return this.createdAt;
    }

}
