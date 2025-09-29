package xyz.luotao.v1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.luotao.v1.entity.RolePermissions;

import java.util.List;
import java.util.Optional;

@Mapper
public interface IRolePermissionMapper {

    //通过角色ID查询权限ID列表
    @Select("SELECT * FROM role_permissions WHERE role_id = #{roleId}")
    List<RolePermissions> findByRoleId(Long roleId);
}
