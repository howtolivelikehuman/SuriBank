package com.howtolivelikehuman.suribank.service;

import javax.servlet.http.HttpSession;

import com.howtolivelikehuman.suribank.dto.UserVO;

public interface UserService {

	//회원가입
	public boolean signup(UserVO vo) throws Exception;
	
	//로그인 체크
	public boolean login(UserVO vo, HttpSession session);
	
	//로그아웃
	public void logout(HttpSession session);
}
