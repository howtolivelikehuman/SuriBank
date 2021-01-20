package com.howtolivelikehuman.suribank.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.howtolivelikehuman.suribank.dto.UserVO;

@Repository
public class UserDaoImpl implements UserDao{

	@Inject
	SqlSession sqlSession;
	
	@Override
	public UserVO login(UserVO vo) {
		UserVO user = sqlSession.selectOne("user.login", vo);
		return user;
	}

	@Override
	public void signup(UserVO vo) {
		sqlSession.selectOne("user.signup", vo);
	}
}
