package xyz.luotao.v1.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.mapper.IUserMapper;
import xyz.luotao.v1.mapper.IUserRoleMapper;

@RestController
@RequestMapping("/api/role")
public class UserRoleController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserRoleMapper userRoleMapper;

    /**
     * 修改用户角色
     * @param roleId 角色ID: 1-组织者 2-管理员 3-版主 4-普通用户 5-封禁用户
     * @param session
     * @return
     */
    @PutMapping
    public ResponseEntity<ResponseMessage> updateUserRole(Long roleId, HttpSession session){
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        boolean success = userRoleMapper.updateUserRole(user.getId(), roleId);
        if (!success) {
            return ResponseEntity
                    .status(500)
                    .body(new ResponseMessage(500, "修改用户角色失败", null));
        }
        log.info("成功修改用户角色：userId={}, roleId={}", user.getId(), roleId);
        return ResponseEntity.ok(ResponseMessage.success());
    }

}
