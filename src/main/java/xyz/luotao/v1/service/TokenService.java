package xyz.luotao.v1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.mapper.IUserMapper;
import xyz.luotao.v1.mapper.IUserRoleMapper;

import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private IUserRoleMapper userRoleMapper;
    @Autowired
    private IUserMapper userMapper;

    //添加用户角色激活token
    public String addTokenUserRole(String email) {
        String uuid = UUID.randomUUID().toString();
        return userMapper.FindByEmail(email)
                .filter(user -> userRoleMapper.addTokenUserRole(user.getId(), uuid))
                .map(user -> uuid)
                .orElse(null);
    }

    //根据email＋token激活用户
    public boolean verifyActivateUser(String email, String token) {
        return userMapper.FindByEmail(email)
                .map(user -> {
                    String activationCodeByUserId = userRoleMapper.getActivationCodeByUserId(user.getId());
                    if (activationCodeByUserId == null || !activationCodeByUserId.equals(token)) {
                        return false;
                    }
                    //激活用户
                    return userRoleMapper.updateUserRole(user.getId(), 4L);
                })
                .orElse(false);
    }
}
