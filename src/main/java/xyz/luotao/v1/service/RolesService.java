package xyz.luotao.v1.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.mapper.IUserRoleMapper;

@Service
public class RolesService {

    @Autowired
    private IUserRoleMapper userRoleMapper;

    public ResponseEntity<ResponseMessage> enforcePostPermission(User user) {
        if (user == null) {
            return ResponseEntity.status(401)
                    .body(new ResponseMessage(401, "未登录", null));
        }
        Long roleId = userRoleMapper.getRoleIdByUserId(user.getId());
        if (roleId == null || roleId == 5L) {
            return ResponseEntity.status(403)
                    .body(new ResponseMessage(403, "权限不足，禁止发布文章", null));
        }
        return null; // 通过校验
    }

}
