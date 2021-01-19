 package com.howtolivelikehuman.suribank.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.howtolivelikehuman.suribank.dto.User;
import com.howtolivelikehuman.suribank.service.Service;
import com.howtolivelikehuman.suribank.service.SignUpService;

@Controller
public class SignUpController {
	
	Service command;
	
	@RequestMapping("signup_view")
	public String Signup_view(Model model) {
			
		System.out.println("signup_view");
		return "signup_view";
	}
	
	@RequestMapping("signup")
	public String Signup(Model model, User user) throws Exception {
		
		model.addAttribute("user", user);
		
		command = new SignUpService();
		command.execute(model);
		
		
		System.out.println("signup");
		return "signup";
	}
	
	
}
