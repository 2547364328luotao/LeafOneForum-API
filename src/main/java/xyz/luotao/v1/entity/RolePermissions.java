package xyz.luotao.v1.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
* 角色-权限 关系
* @TableName RolePermissions
*/
@ToString
@TableName(value ="role_permissions")
public class RolePermissions implements Serializable {

    /**
    * 
    */
    //@NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long roleId;
    /**
    * 
    */
    //@NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long permissionId;
    /**
    * 
    */
    //@NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date createdAt;

    /**
    * 
    */
    public void setRoleId(Long roleId){
    this.roleId = roleId;
    }

    /**
    * 
    */
    public void setPermissionId(Long permissionId){
    this.permissionId = permissionId;
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
    public Long getRoleId(){
    return this.roleId;
    }

    /**
    * 
    */
    public Long getPermissionId(){
    return this.permissionId;
    }

    /**
    * 
    */
    public Date getCreatedAt(){
    return this.createdAt;
    }

}
