package com.uos.suribank.dao;

import java.util.List;

import com.uos.suribank.dto.UserVO;

public interface UserDAO {
	public UserVO login(UserVO user) throws Exception;
	public int signup(UserVO user);
	public int checkId(String id);
	
	//전체 유저리스트 받아오기
	public List<UserVO> getAllUsers();

	public UserVO getInfo(String id);
	public int deleteInfo(UserVO user);
	public int updateInfo(UserVO user);
}
