package com.huzb.spike.dao;

import com.huzb.spike.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
	
	@Select("select * from user where id = #{id}")
	public User getById(@Param("id") long id);

	@Update("update user set password = #{password} where id = #{id}")
    void updatePassword(User newUser);
}
