package xyz.luotao.v1.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepliesDto {
    @NotNull
    @Positive
    private Long postId;

    @NotNull
    private String content;
}
