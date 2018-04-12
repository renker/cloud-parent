package com.renker.cloud.security.rest.service;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.renker.cloud.security.model.User;
import com.renker.cloud.security.service.IUserService;

@RestController
@RequestMapping("security/userRestService")
public class UserServiceRestImpl implements IUserRestService{
	
	@Resource
	private IUserService userService;

	@Override
	public int save(User user) {
		return userService.save(user);
	}

	@Override
	public User findById(@RequestParam("id")String id) {
		return userService.findById(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User user) {
		return userService.updateByPrimaryKeySelective(user);
	}

	@Override
	public User findByAccount(String account) {
		return userService.findByAccount(account);
	}
	
	@Override
	public User findByAccountAndPassword(String account, String password) {
		return userService.findByAccountAndPassword(account, password);
	}

}
