package net.yxy.chukonu.spring.security.mybatis.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import net.yxy.chukonu.spring.security.entity.UserEntity;

public interface SecurityMapper {

    
    @Select("SELECT * FROM USERS WHERE NAME = #{username}")
    @Results({ @Result(property = "username", column = "NAME"),
               @Result(property = "role", column = "ROLE") })
    UserEntity getOne(String username);

    
    
}
