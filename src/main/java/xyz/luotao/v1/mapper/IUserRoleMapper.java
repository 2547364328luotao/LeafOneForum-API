package xyz.luotao.v1.mapper;

import org.apache.ibatis.annotations.*;
import xyz.luotao.v1.entity.UserRoles;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface IUserRoleMapper {

    //分配用户角色
        @Insert({
        "<script>",
        "INSERT INTO user_roles (user_id, role_id",
        "<if test='grantedByUserId != null'>, granted_by_user_id</if>",
        "<if test='effectiveAt != null'>, effective_at</if>",
        "<if test='expiresAt != null'>, expires_at</if>",
        "<if test='createdAt != null'>, created_at</if>",
        ") VALUES (#{userId}, #{roleId}",
        "<if test='grantedByUserId != null'>, #{grantedByUserId}</if>",
        "<if test='effectiveAt != null'>, #{effectiveAt}</if>",
        "<if test='expiresAt != null'>, #{expiresAt}</if>",
        "<if test='createdAt != null'>, #{createdAt}</if>",
        ")",
        "</script>"
    })
    boolean assignRoleToUser(@Param("userId") Long userId,
                             @Param("roleId") Long roleId,
                             @Param("grantedByUserId") Long grantedByUserId,
                             @Param("effectiveAt") LocalDateTime effectiveAt,
                             @Param("expiresAt") LocalDateTime expiresAt,
                             @Param("createdAt") LocalDateTime createdAt);

    //撤销角色
    @Delete("DELETE FROM user_roles WHERE user_id = #{userId} AND role_id = #{roleId}")
    boolean revokeRoleFromUser(@Param("userId") Long userId, @Param("roleId") Long roleId);

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
    //@Select("SELECT * FROM user_roles WHERE user_id = #{userId} AND expires_at > #{date}")
    @Select("SELECT * FROM user_roles WHERE user_id = #{userId}")
    List<UserRoles> findByUserIdAndExpiresAtAfter(Long userId, LocalDateTime date);
}
