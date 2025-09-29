package xyz.luotao.v1.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import xyz.luotao.v1.controller.UserController;
import xyz.luotao.v1.entity.Permission;
import xyz.luotao.v1.entity.RolePermissions;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.entity.UserRoles;
import xyz.luotao.v1.mapper.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ForumUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserRoleMapper userRoleMapper;
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IRepliesMapper repliesMapper;
    @Autowired
    private IRolePermissionMapper rolePermissionMapper;
    @Autowired
    private IPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("待认证邮箱：" + email);
        User user = userMapper.FindByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在" + email));
        log.info("待认证用户：" + user);

        //获取用户当前有效的角色
        List<UserRoles> userRolesList = userRoleMapper.findByUserIdAndExpiresAtAfter(
                user.getId(), LocalDateTime.now());
        log.info("用户现有角色" + userRolesList);

        //从角色中提取权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRoles userRoles : userRolesList) {
            // 获取角色ID
            Long roleId = userRoles.getRoleId();
            log.info("用户现有角色ID" + roleId);
            // 查询角色对应的权限
            List<RolePermissions> rolePermissionsList = rolePermissionMapper.findByRoleId(roleId);
            for (RolePermissions rolePermissions : rolePermissionsList) {
                //log.info("角色现有权限ID" + rolePermissions);
                Long permissionId = rolePermissions.getPermissionId();
                Permission permission = permissionMapper.findById(permissionId);
                if (permission != null) {
                    log.info("用户现有权限" + permission.getCode());
                    authorities.add(new SimpleGrantedAuthority(permission.getCode()));
                }
            }
        }


        return new ForumUserDetails(user, authorities);
    }
}











