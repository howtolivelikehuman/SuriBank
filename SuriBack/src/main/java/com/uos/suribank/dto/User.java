package com.uos.suribank.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
	private Long no;

	@Size(min=4, max=30, message = "id는 4~20자로 입력해야 합니다.")
	@Column(length  = 30, nullable = false, unique = true, columnDefinition = "CHAR(30)")
	String id;

	//@Size(min=8, max=20, message = "password는 8~20자로 입력해야 합니다.")
	@Column(length  = 20, nullable = false, columnDefinition = "CHAR(20)")
	String password;

	@Size(min=2, max=8, message = "이름은 2~8자로 입력해야 합니다.")
	@Column(length  = 8, nullable = false)
	String name;

	@Size(min=1, max=20, message = "별명은 1~20자로 입력해야 합니다.")
	@Column(length  = 20, nullable = false)
	String nickname;

	@Column(length  = 20)
	String major;
	
	//T1 is the lowest. T5 is admin
	@Column(nullable = false, length = 2, columnDefinition = "CHAR(2) DEFAULT 'T1'")
	String type;	

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Date registerdate;

	@Column(columnDefinition = "INT DEFAULT 0")
	int point;
}
