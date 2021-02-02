package com.uos.suribank.dao;

import java.util.List;

import com.uos.suribank.dto.User;

public interface UserDAO {
	public User login(User user) throws Exception;
	public int signup(User user);
	public int checkId(String id);
	
	//전체 유저리스트 받아오기
	public List<User> getAllUsers();

	public User getInfo(String id);
	public int deleteInfo(User user);
	public int updateInfo(User user);
}
