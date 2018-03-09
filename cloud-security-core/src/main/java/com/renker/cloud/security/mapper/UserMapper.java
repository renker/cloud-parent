package com.renker.cloud.security.mapper;

import org.apache.ibatis.annotations.Param;

import com.renker.cloud.security.model.User;

public interface UserMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User findByAccount(String account);
    
    User findByAccountAndPassword(@Param("account")String account, @Param("password")String password);
    
}