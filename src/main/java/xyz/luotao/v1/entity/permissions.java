package xyz.luotao.v1.entity;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 权限表
* @TableName permissions
*/
public class permissions implements Serializable {

    /**
    * 
    */
    //@NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 权限唯一编码，如 forum.thread.delete
    */
    //@NotBlank(message="[权限唯一编码，如 forum.thread.delete]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("权限唯一编码，如 forum.thread.delete")
    @Length(max= 100,message="编码长度不能超过100")
    private String code;
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
    * 权限唯一编码，如 forum.thread.delete
    */
    public void setCode(String code){
    this.code = code;
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
    * 权限唯一编码，如 forum.thread.delete
    */
    public String getCode(){
    return this.code;
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
    * 
    */
    public Date getCreatedAt(){
    return this.createdAt;
    }

}
