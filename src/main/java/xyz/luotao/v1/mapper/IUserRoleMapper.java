package xyz.luotao.v1.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IUserRoleMapper {

    // 添加用户的角色
    @Insert("INSERT INTO user_roles(user_id) VALUES (#{userId})")
    boolean addUserRole(@Param("userId") Long userId);

    // 添加用户激活校验令牌
    @Insert("INSERT INTO user_roles(user_id, activation_code) VALUES (#{userId}, #{activationCode})")
    boolean addTokenUserRole(@Param("userId") Long userId, @Param("activationCode") String activationCode);

    // 修改用户的角色
    @Update("UPDATE user_roles SET role_id = #{roleId} WHERE user_id = #{userId}")
    boolean updateUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    // 根据 userId 取 activation_code
    @Select("SELECT activation_code FROM user_roles WHERE user_id = #{userId}")
    String getActivationCodeByUserId(@Param("userId") Long userId);

    // 根据用户ID查询用户角色ID
    @Select("SELECT role_id FROM user_roles WHERE user_id = #{userId}")
    Long getRoleIdByUserId(@Param("userId") Long userId);
}
