package com.uos.suribank.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserVO implements Serializable{
	int no;
	String id;
	String password;
	String name;
	String nickname;
	String email;
	String major;
	//T1 is the lowest. T5 is admin
	String type;	
	String registerdate;
	int point;
	
	public String getId(){
		return this.id;
	}
}
