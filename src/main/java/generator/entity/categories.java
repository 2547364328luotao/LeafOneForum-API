package generator.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 文章分类表
* @TableName categories
*/
public class categories implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 分类名称
    */
    @NotBlank(message="[分类名称]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("分类名称")
    @Length(max= 100,message="编码长度不能超过100")
    private String name;
    /**
    * 唯一短标识（用于URL）
    */
    @NotBlank(message="[唯一短标识（用于URL）]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("唯一短标识（用于URL）")
    @Length(max= 100,message="编码长度不能超过100")
    private String slug;
    /**
    * 分类简介
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("分类简介")
    @Length(max= 255,message="编码长度不能超过255")
    private String description;
    /**
    * 父级分类（可空）
    */
    @ApiModelProperty("父级分类（可空）")
    private Long parentId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date createdAt;

    /**
    * 
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 分类名称
    */
    public void setName(String name){
    this.name = name;
    }

    /**
    * 唯一短标识（用于URL）
    */
    public void setSlug(String slug){
    this.slug = slug;
    }

    /**
    * 分类简介
    */
    public void setDescription(String description){
    this.description = description;
    }

    /**
    * 父级分类（可空）
    */
    public void setParentId(Long parentId){
    this.parentId = parentId;
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
    * 分类名称
    */
    public String getName(){
    return this.name;
    }

    /**
    * 唯一短标识（用于URL）
    */
    public String getSlug(){
    return this.slug;
    }

    /**
    * 分类简介
    */
    public String getDescription(){
    return this.description;
    }

    /**
    * 父级分类（可空）
    */
    public Long getParentId(){
    return this.parentId;
    }

    /**
    * 
    */
    public Date getCreatedAt(){
    return this.createdAt;
    }

}
