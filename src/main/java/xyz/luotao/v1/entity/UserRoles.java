package xyz.luotao.v1.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

/**
* 用户-角色 关系
* @TableName UserRoles
*/
@TableName(value ="user_roles")
public class UserRoles implements Serializable {

    /**
    * 
    */
    //@NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long userId;
    /**
    * 
    */
    //@NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long roleId;
    /**
    * 授予者（可空）
    */
    @ApiModelProperty("授予者（可空）")
    private Long grantedByUserId;
    /**
    * 生效时间
    */
    //@NotNull(message="[生效时间]不能为空")
    @ApiModelProperty("生效时间")
    private Date effectiveAt;
    /**
    * 过期时间（可空）
    */
    @ApiModelProperty("过期时间（可空）")
    private Date expiresAt;
    /**
    * 
    */
    //@NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date createdAt;

    //激活验证码
    @ApiModelProperty("")
    private String activatioCnode;

    /**
    * 
    */
    public void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 
    */
    public void setRoleId(Long roleId){
    this.roleId = roleId;
    }

    /**
    * 授予者（可空）
    */
    public void setGrantedByUserId(Long grantedByUserId){
    this.grantedByUserId = grantedByUserId;
    }

    /**
    * 生效时间
    */
    public void setEffectiveAt(Date effectiveAt){
    this.effectiveAt = effectiveAt;
    }

    /**
    * 过期时间（可空）
    */
    public void setExpiresAt(Date expiresAt){
    this.expiresAt = expiresAt;
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
    public Long getUserId(){
    return this.userId;
    }

    public String getActivatioCnode() {
        return activatioCnode;
    }

    public void setActivatioCnode(String activatioCnode) {
        this.activatioCnode = activatioCnode;
    }

    /**
    * 
    */
    public Long getRoleId(){
    return this.roleId;
    }

    /**
    * 授予者（可空）
    */
    public Long getGrantedByUserId(){
    return this.grantedByUserId;
    }

    /**
    * 生效时间
    */
    public Date getEffectiveAt(){
    return this.effectiveAt;
    }

    /**
    * 过期时间（可空）
    */
    public Date getExpiresAt(){
    return this.expiresAt;
    }

    /**
    * 
    */
    public Date getCreatedAt(){
    return this.createdAt;
    }

}
