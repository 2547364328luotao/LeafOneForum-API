package xyz.luotao.v1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.luotao.v1.entity.Role;

import java.util.Optional;

@Mapper
public interface IRoleMapper extends BaseMapper<Role> {

    @Select("SELECT * FROM role WHERE name = #{name} LIMIT 1")
    Optional<Role> selectByName(String name);
}