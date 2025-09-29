package xyz.luotao.v1.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        User user = userMapper.FindByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在" + email));

        //获取用户当前有效的角色
        List<UserRoles> userRolesList = userRoleMapper.findByUserIdAndExpiresAtAfter(
                user.getId(), LocalDateTime.now());

        //从角色中提取权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRoles userRoles : userRolesList) {
            // 获取角色ID
            Long roleId = userRoles.getRoleId();
            // 查询角色对应的权限
            List<RolePermissions> rolePermissionsList = rolePermissionMapper.findByRoleId(roleId);
            for (RolePermissions rolePermissions : rolePermissionsList) {
                Long permissionId = rolePermissions.getPermissionId();
                Permission permission = permissionMapper.findById(permissionId);
                if (permission != null) {
                    authorities.add(new SimpleGrantedAuthority(permission.getCode()));
                }
            }
        }


        return new ForumUserDetails(user, authorities);
    }
}











