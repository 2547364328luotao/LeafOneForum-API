package xyz.luotao.v1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.luotao.v1.entity.Permission;

import java.util.Optional;

@Mapper
public interface IPermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT * FROM permission WHERE code = #{code} LIMIT 1")
    Optional<Permission> selectByCode(String code);

    // 根据ID查询权限
    @Select("SELECT * FROM permissions WHERE id = #{permissionId}")
    Permission findById(Long permissionId);
}