package com.howtolivelikehuman.suribank.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.howtolivelikehuman.suribank.dto.UserVO;
import com.howtolivelikehuman.suribank.service.UserService;

@Controller
@RequestMapping(value = "/user/*")
public class UserController {
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Inject
	UserService userService;
	
	//로그인 화면
	@RequestMapping("login_view")
	public String login_view(Model model) {
		
		System.out.println("login_view");
		return "/user/login_view";
	}
	
	//로그인
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@ModelAttribute UserVO vo, HttpSession session, RedirectAttributes rttr) throws Exception {
		System.out.println("login");
		boolean result  = userService.login(vo, session);
		
		if(result) {
			rttr.addFlashAttribute("msg", "로그인에 성공하였습니다.");
		}else {
			rttr.addFlashAttribute("msg", "로그인에 실패하였습니다.");
		}
		
		return "redirect:/";
	}
	
	//로그아웃
	@RequestMapping("logout")
	public String logout(Model model, HttpSession session, RedirectAttributes rttr) {
		userService.logout(session);
		
		rttr.addFlashAttribute("msg", "로그아웃 하였습니다.");
		
		return "redirect:/";
	}
	
	@RequestMapping("signup_view")
	public String Signup_view(Model model) {
			
		System.out.println("signup_view");
		return "/user/signup_view";
	}
	
	@RequestMapping("signup")
	public String Signup(Model model, UserVO vo, RedirectAttributes rttr) throws Exception {
		boolean result = userService.signup(vo);
		System.out.println("signup");
		
		if(result) {
			rttr.addFlashAttribute("msg", "회원가입에 성공하였습니다.");
		}else {
			rttr.addFlashAttribute("msg", "회원가입에 실패하였습니다.");
		}
		
		
		return "redirect:/";
	}
}
