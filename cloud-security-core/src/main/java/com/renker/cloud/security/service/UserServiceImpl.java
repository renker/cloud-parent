package com.renker.cloud.security.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renker.cloud.security.mapper.UserMapper;
import com.renker.cloud.security.model.User;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	
	@Resource
	private UserMapper userMapper;

	@Override
	public int save(User user) {
		return userMapper.insert(user);
	}

	@Override
	public User findById(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public User findByAccount(String account) {
		return null;
	}
	
	@Override
	public User findByAccountAndPassword(String account, String password) {
		return null;
	}

}
