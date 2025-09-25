package xyz.luotao.v1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.entity.dto.UserDto;
import xyz.luotao.v1.mapper.IUserMapper;

@Service
public class LoginService {

    @Autowired
    private IUserMapper userMapper;


    public User validateLogin(@RequestBody UserDto userDto) {
        User user = userMapper.FindByEmail(userDto.getEmail());
        if(user != null && user.getPasswordHash().equals(userDto.getPasswordHash())) {
            return user;
        }
        return null;
    }


}
