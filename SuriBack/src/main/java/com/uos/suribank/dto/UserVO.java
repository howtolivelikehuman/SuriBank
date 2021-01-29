package com.uos.suribank.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable{
	int _no;
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
	
}
