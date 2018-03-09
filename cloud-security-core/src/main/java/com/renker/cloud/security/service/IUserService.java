package com.renker.cloud.security.service;

import org.springframework.web.bind.annotation.RequestParam;

import com.renker.cloud.security.model.User;

public interface IUserService {
	public int save(User user);
	
	public User findById(@RequestParam("id") String id);
	
	User findByAccount(String account);
	
	int updateByPrimaryKeySelective(User user);
	
	User findByAccountAndPassword(String account,String password);
}
