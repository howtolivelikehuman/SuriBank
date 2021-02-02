package com.uos.suribank.entity;

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

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@DynamicUpdate
@DynamicInsert
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
	private Long no;

	
	@Column(length  = 30, nullable = false, unique = true, columnDefinition = "CHAR(30)")
	String id;

	
	@Column(length  = 20, nullable = false, columnDefinition = "CHAR(20)")
	String password;

	
	@Column(length  = 8, nullable = false)
	String name;

	
	@Column(length  = 20, nullable = false, unique = true)
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
