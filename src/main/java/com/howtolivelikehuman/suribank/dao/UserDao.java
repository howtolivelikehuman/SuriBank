package com.howtolivelikehuman.suribank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.howtolivelikehuman.suribank.dto.User;
import com.howtolivelikehuman.suribank.util.Constant;

@Repository("userDao")
public class UserDao {
	private static UserDao dao;
	private static JdbcTemplate template = null;
	//private static DataSource ds;
	private String sql;
	
	@Resource(name="sqlSessoinTemplate")
	private SqlSessionTemplate session;
	
	
	static {
		try {
			System.out.println("start DBCP!");
			//Context context = new InitialContext();
			//ds = (DataSource) context.lookup("java:comp/env/jdbc/mysql");
			template = Constant.template;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//ΩÃ±€≈Ê ∆–≈œ
	private UserDao() {}
	public synchronized static UserDao getInstance() {
		if(dao == null) {
			dao = new UserDao();
		}
		return dao;
	}
	
	
	public void insert(User user){
		session.insert("userDB.insertUser", user);
	}
	
	public User select(User user){
		return session.selectOne("userDB.selectUser",user);
	}

}
