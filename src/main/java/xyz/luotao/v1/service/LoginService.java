package xyz.luotao.v1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.entity.dto.UserDto;
import xyz.luotao.v1.mapper.IUserMapper;

@Service
public class LoginService {

    @Autowired
    private IUserMapper userMapper;


    public User validateLogin(UserDto userDto) {
        // FindByEmail 返回 Optional<User>，这里安全地获取并校验密码
        return userMapper.FindByEmail(userDto.getEmail())
                .filter(u -> u.getPasswordHash().equals(userDto.getPasswordHash()))
                .orElse(null);
    }


}
