package com.howtolivelikehuman.suribank.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.howtolivelikehuman.suribank.service.LogInService;
import com.howtolivelikehuman.suribank.service.Service;
import com.howtolivelikehuman.suribank.util.Constant;

@Controller
public class LogInController {
	
	Service command;
	/*public JdbcTemplate template;
	
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = this.template;
	}*/
	
	@RequestMapping("/login_view")
	public String Login_view(Model model) {
		
		System.out.println("login_view");
		return "login_view";
	}
	
	
	@RequestMapping("/login")
	public String Login(HttpServletRequest request, Model model) throws Exception {

		System.out.println("login");
		command = new LogInService();
		command.execute(model);

		return "login";
	}
	
}
