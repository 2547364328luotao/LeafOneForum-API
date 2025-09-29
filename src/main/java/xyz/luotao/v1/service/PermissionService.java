package xyz.luotao.v1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luotao.v1.entity.Permission;
import xyz.luotao.v1.entity.Role;
import xyz.luotao.v1.entity.RolePermissions;
import xyz.luotao.v1.entity.UserRoles;
import xyz.luotao.v1.mapper.IPermissionMapper;
import xyz.luotao.v1.mapper.IRoleMapper;
import xyz.luotao.v1.mapper.IRolePermissionMapper;
import xyz.luotao.v1.mapper.IUserRoleMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private IUserRoleMapper userRoleMapper;
    @Autowired
    private IRoleMapper roleMapper;
    @Autowired
    private IRolePermissionMapper rolePermissionMapper;
    @Autowired
    private IPermissionMapper permissionMapper;



    public boolean hasPermission(Long userId, String permissionCode) {
        // 获取用户当前有效的角色
        List<UserRoles> userRolesList = userRoleMapper.findByUserIdAndExpiresAtAfter(
                userId, LocalDateTime.now());
        // 检查这些角色是否包含指定权限
        for (UserRoles userRole : userRolesList) {
            Long roleId = userRole.getRoleId();
            List<RolePermissions> byRoleIdList = rolePermissionMapper.findByRoleId(roleId);
            for (RolePermissions RolePermission : byRoleIdList) {
                Permission permission = permissionMapper.findById(RolePermission.getPermissionId());
                if (permission.getCode().equals(permissionCode)) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Role> getUserRoles(Long userId) {
        List<UserRoles> userRoles = userRoleMapper.findByUserId(userId);
        if (userRoles == null || userRoles.isEmpty()) {
            return Collections.emptyList();
        }
        return userRoles.stream()
                .map(UserRoles::getRoleId)
                .filter(Objects::nonNull)
                .map(roleMapper::selectById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void assignRoleToUser(Long userId, Long roleId, Long grantedByUserId,LocalDateTime effectiveAt,LocalDateTime expiresAt) {
        // 实现分配角色的逻辑
        boolean success = userRoleMapper.assignRoleToUser(userId, roleId, grantedByUserId, effectiveAt, expiresAt, LocalDateTime.now());
        if (!success) {
            throw new RuntimeException("分配角色失败");
        }
    }

    public void revokeRoleFromUser(Long userId, Long roleId) {
        // 实现撤销角色的逻辑
        boolean success = userRoleMapper.revokeRoleFromUser(userId, roleId);
        if (!success) {
            throw new RuntimeException("撤销角色失败");
        }
    }
}
