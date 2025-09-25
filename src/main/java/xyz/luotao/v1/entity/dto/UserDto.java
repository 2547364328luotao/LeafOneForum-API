package xyz.luotao.v1.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private  String nickname;
    private String email;
    private String passwordHash;
}
