/*
 * @Author: Luo Tao 18727430326@163.com
 * @Date: 2025-09-29 16:11:08
 * @LastEditors: Luo Tao 18727430326@163.com
 * @LastEditTime: 2025-09-29 17:31:59
 * @FilePath: \v1-security\src\main\java\xyz\luotao\v1\mapper\IUserMapper.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package xyz.luotao.v1.mapper;

import org.apache.ibatis.annotations.*;
import xyz.luotao.v1.entity.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface IUserMapper {
    //查询所有用户
    @Select("SELECT * FROM users")
    public List<User> FindAll();
    //根据邮箱查询用户
    @Select("SELECT * FROM users WHERE email = #{email}")
    public Optional<User> FindByEmail(String email);
    //根据邮箱查询用户是否存在
    @Select("SELECT COUNT(*) FROM user WHERE email = #{email}")
    boolean existsByEmail(String email);
    //根据ID查询用户
    @Select("SELECT id,nickname,email,room_number FROM users WHERE id = #{id}")
    @Results(
            {
                    @Result(column = "id", property = "id"),
                    @Result(column = "nickname", property = "nickname"),
                    @Result(column = "email", property = "email"),
                    @Result(column = "room_number", property = "roomNumber"),
                    @Result(column = "id",property = "posts",javaType = List.class,
                            many = @Many(select = "xyz.luotao.v1.mapper.IPostMapper.FindPostsByAuthorId"))
            }
    )
    public User FindById(Long id);


    //增加用户
    @Insert("INSERT INTO users(nickname, email,password_hash) VALUES(#{nickname}, #{email},#{passwordHash})")
    public boolean AddUser(User user);
    //添加用户宿舍信息
    @Update("UPDATE users SET room_number = #{roomNumber}, room_meter_password = #{roomMeterPassword} WHERE id = #{id}")
    public boolean addDormitoryInfo(User user);

    //删除用户
    @Delete("DELETE FROM users WHERE id = #{id}")
    public boolean DeleteUser(Integer id);

    //更改用户信息
    @Update("UPDATE users SET nickname = #{nickname}, email = #{email}, password_hash = #{passwordHash} WHERE id = #{id}")
    public boolean UpdateUser(User user);

}
