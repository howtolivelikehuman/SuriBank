package com.howtolivelikehuman.suribank.dto;

import java.sql.Timestamp;

public class User {	
	int no;
	String id;
	String password;
	String name;
	String nickname;
	String email;
	//T1 is the lowest. T5 is admin
	String type;	
	Timestamp inDate;
	
	public User() {
		
	}
	
	public User(String id, String password, String name, String nickname, 
			String email, String type, Timestamp inDate) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.type = type;
		this.inDate = inDate;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getInDate() {
		return inDate;
	}
	public void setInDate(Timestamp inDate) {
		this.inDate = inDate;
	}
}
