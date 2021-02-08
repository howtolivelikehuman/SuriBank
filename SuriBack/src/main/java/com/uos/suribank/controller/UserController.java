package com.uos.suribank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.suribank.dto.UserDTO.infoDTO;
import com.uos.suribank.dto.UserDTO.loginDTO;
import com.uos.suribank.dto.UserDTO.signupDTO;
import com.uos.suribank.dto.UserDTO.updateDTO;
import com.uos.suribank.service.UserService;

import com.uos.suribank.exception.UserNotFoundException;
import com.uos.suribank.exception.DuplicateException;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	//조회
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getInfo(@PathVariable Long id) {
		
		infoDTO info = userService.getInfo(id);

		if(info == null){
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		/*SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("no","id","name","nickname","major","type","registerdate","point");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(info);
        mapping.setFilters(filters);
		return ResponseEntity.ok(mapping);*/
		return ResponseEntity.ok(info);
	}

	//삭제
	@DeleteMapping("/{id}")
	public void deleteInfo(@PathVariable Long id) {
		int result = userService.deleteInfo(id);
		if( result == 0){
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
	}

	//수정
	@PutMapping(path = "/{id}")
	public void updateInfo(@PathVariable Long id, @Valid @RequestBody updateDTO udto){
		
		if(userService.update(udto, id) == null){
			throw new UserNotFoundException("Error Occurs");
		}
	}

	//로그인
	@PostMapping(value = "/login")
	public void login(@RequestBody loginDTO ldto){
		boolean result = userService.login(ldto);
		
		//loginfailed
		if(!result){
			throw new UserNotFoundException("ID is not found or Wrong PW");
		}
	}

	//아이디 검사
	@PostMapping(value = "checkId")
	public void checkId(@RequestBody loginDTO ldto) {
		boolean result = userService.checkId(ldto.getEmail());

		//loginfailed
		if(result){
			throw new DuplicateException("ID is duplicated");
		}
	}

	//회원가입
	@PostMapping("/signup")
	public void signup(@Valid @RequestBody signupDTO sdto) {
		
		// 아이디 한번 더 검사
		userService.checkId(sdto.getEmail());

		if(userService.singup(sdto) == null){
			throw new UserNotFoundException("Error Occurs");
		}
	}
	
}
