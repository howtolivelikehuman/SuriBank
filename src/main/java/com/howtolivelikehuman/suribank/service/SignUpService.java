package com.howtolivelikehuman.suribank.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.howtolivelikehuman.suribank.dao.UserDao;
import com.howtolivelikehuman.suribank.dto.User;

public class SignUpService implements Service{

	@Override
	public int execute(Model model) throws Exception {
		
		Map<String, Object> map = model.asMap();
		try {
			
			User user = (User) map.get("user");
			UserDao userdao = UserDao.getInstance();
			
			userdao.insert(user);
			
		}catch (Exception e) {
			return 0;
		}
		return 1;
	}

}
