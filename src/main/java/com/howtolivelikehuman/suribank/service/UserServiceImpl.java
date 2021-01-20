package com.howtolivelikehuman.suribank.service;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.howtolivelikehuman.suribank.dao.UserDao;
import com.howtolivelikehuman.suribank.dto.UserVO;

@Service
public class UserServiceImpl implements UserService{
	@Inject
	UserDao userDao;
	
	@Override
	public boolean signup(UserVO vo) throws Exception {
		boolean result = true;
		try{
			userDao.signup(vo);
		
		}catch(Exception e) {
			result = false;
		}
		return result;
	}
	
	//로그인
	@Override
	public boolean login(UserVO vo, HttpSession session) {
		boolean result = false;
		UserVO user = userDao.login(vo);
		if(user != null) {
			session.setAttribute("user", user);
			result = true;
		}
		else {
			session.setAttribute("user", null);
		}
		
		return result;
	}
	
	//로그아웃
	@Override
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
