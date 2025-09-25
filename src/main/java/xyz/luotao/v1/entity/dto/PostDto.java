package xyz.luotao.v1.entity.dto;

// 帖子数据传输对象（用于新增/编辑帖子时接收前端参数）
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    // 帖子ID（编辑时必传）
    private Long id;
    // 分类ID
    private Long categoryId;
    // 帖子标题
    private String title;
    // 封面图片URL
    private String coverUrl;
    // 摘要/简介
    private String synopsis;
    // 帖子内容
    private String content;
}
