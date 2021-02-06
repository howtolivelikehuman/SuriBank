package com.uos.suribank.controller;

import java.util.List;

import com.uos.suribank.dto.UserDTO.infoDTO;
import com.uos.suribank.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminUserController {
	@Autowired
	private UserService userService;

	@GetMapping("/user/list")
	public List<infoDTO> userlist(){
		return userService.getUserList();
	}
}
