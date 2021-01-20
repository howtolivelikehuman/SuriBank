package com.howtolivelikehuman.suribank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.howtolivelikehuman.suribank.dto.UserVO;


public interface UserDao {
	
	public UserVO login(UserVO user);
	public void signup(UserVO user);
}
