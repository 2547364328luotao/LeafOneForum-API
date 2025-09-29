/*
 * @Author: Luo Tao 18727430326@163.com
 * @Date: 2025-09-29 16:11:08
 * @LastEditors: Luo Tao 18727430326@163.com
 * @LastEditTime: 2025-09-29 22:12:42
 * @FilePath: \v1-security\src\main\java\xyz\luotao\v1\mapper\IUserRoleMapper.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package xyz.luotao.v1.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import xyz.luotao.v1.entity.UserRoles;

import java.time.LocalDateTime;
import java.util.List;

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

    // 根据用户ID查询用户角色信息
    @Select("SELECT * FROM user_roles WHERE user_id = #{userId}")
    List<UserRoles> findByUserId(Long userId);

    // 根据用户ID和当前时间查询未过期的用户角色信息
    @Select("SELECT * FROM user_roles WHERE user_id = #{userId} AND expires_at > #{date}")
    List<UserRoles> findByUserIdAndExpiresAtAfter(Long userId, LocalDateTime date);
}
