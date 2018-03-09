package com.renker.cloud.security.rest.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.renker.cloud.security.model.User;

@RequestMapping("userService")
public interface IUserRestService {
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public int save(User user);
	
	@RequestMapping(value="findById",method=RequestMethod.GET)
	public User findById(@RequestParam("id") String id);
	
	@RequestMapping(value="findByAccount",method=RequestMethod.GET)
	User findByAccount(String account);
	
	@RequestMapping(value="updateByPrimaryKeySelective",method=RequestMethod.POST)
	int updateByPrimaryKeySelective(User user);
	
	@RequestMapping(value="findByAccountAndPassword",method=RequestMethod.GET)
	User findByAccountAndPassword(String account,String password);
}
