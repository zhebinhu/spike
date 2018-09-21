package com.huzb.splike.dao;

import com.huzb.splike.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/9
 */

@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id")int id);

    @Insert("insert into user(id,name) values(#{id},#{name})")
    public int insert(User user);
}
