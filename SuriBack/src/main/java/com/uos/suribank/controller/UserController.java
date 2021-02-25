package com.uos.suribank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.uos.suribank.auth.JwtTokenProvider;
import com.uos.suribank.dto.UserDTO.infoDTO;
import com.uos.suribank.dto.UserDTO.loginDTO;
import com.uos.suribank.dto.UserDTO.signupDTO;
import com.uos.suribank.dto.UserDTO.updateDTO;
import com.uos.suribank.dto.UserDTO.usertokenDTO;
import com.uos.suribank.service.UserService;

import com.uos.suribank.exception.NotFoundException;
import com.uos.suribank.exception.DuplicateException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

	private final PasswordEncoder passwordEncoder;
	
	//조회
	@GetMapping(path = "/")
	public ResponseEntity<?> getInfo(Authentication authentication) {
		Long id = Long.parseLong(authentication.getName());
		infoDTO info = userService.getInfo(id);

		if(info == null){
			throw new NotFoundException(String.format("ID[%s] not found", id));
		}
		return ResponseEntity.ok(info);
	}

	//조회
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getInfo2(@PathVariable Long id) {
		infoDTO info = userService.getInfo(id);

		if(info == null){
			throw new NotFoundException(String.format("ID[%s] not found", id));
		}
		return ResponseEntity.ok(info);
	}

	//삭제
	@DeleteMapping("/")
	public void deleteInfo(Authentication authentication) {
		Long id = Long.parseLong(authentication.getName());
		int result = userService.deleteInfo(id);
		if( result == 0){
			throw new NotFoundException(String.format("ID[%s] not found", id));
		}
	}

	//수정
	@PutMapping(path = "/")
	public void updateInfo(Authentication authentication, @Valid @RequestBody updateDTO udto){
		Long id = Long.parseLong(authentication.getName());
		udto.setPassword(passwordEncoder.encode(udto.getPassword()));
		if(userService.update(udto, id) == null){
			throw new NotFoundException("Error Occurs");
		}
	}

	//로그인
	@PostMapping(value = "/login")
	public String login(@RequestBody loginDTO ldto){
		usertokenDTO ustoken = userService.login(ldto);
		//loginfailed
		if(ustoken == null){
			throw new NotFoundException("ID is not found");
		}else if (!passwordEncoder.matches(ldto.getPassword(), ustoken.getPassword())){
			throw new IllegalArgumentException("Wrong password!");
		}
		return jwtTokenProvider.createToken(ustoken.getId().toString(), ustoken.getType());
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
		sdto.setPassword(passwordEncoder.encode(sdto.getPassword()));
		if(userService.singup(sdto) == null){
			throw new NotFoundException("Error Occurs");
		}
	}
	
}
