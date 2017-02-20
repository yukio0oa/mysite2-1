package com.bit2017.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2017.mysite.repository.UserDao;
import com.bit2017.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public boolean join( UserVo userVo ) {
		boolean result = userDao.insert( userVo );
		return result;
	}
	
	public UserVo getUser( String email, String password ) {
		UserVo userVo = userDao.get(email, password);
		return userVo;
	}
}
