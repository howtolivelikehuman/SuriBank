package com.uos.suribank.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable{
	int _no;
	
	@Size(min=4, max=20, message = "id는 4~20자로 입력해야 합니다.")
	String id;
	//@Size(min=8, max=20, message = "password는 8~20자로 입력해야 합니다.")
	String password;

	@Size(min=2, max=8, message = "이름은 2~8자로 입력해야 합니다.")
	String name;

	@Size(min=1, max=20, message = "별명은 1~20자로 입력해야 합니다.")
	String nickname;

	@Size(max=30, message = "이메일은 30자 이하로 입력해야 합니다.")
	String email;
	String major;
	//T1 is the lowest. T5 is admin
	String type;	
	String registerdate;
	int point;
}
