package com.uos.suribank.dao;

import java.util.List;

import com.uos.suribank.dto.UserVO;

public interface UserDAO {
	public UserVO login(UserVO user) throws Exception;
	public void signup(UserVO user);
	public int checkId(String id);

	public List<UserVO> getAllUsers() throws Exception;
}
