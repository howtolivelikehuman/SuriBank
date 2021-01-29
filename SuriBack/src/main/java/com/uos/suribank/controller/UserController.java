package com.uos.suribank.controller;

import java.net.URI;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uos.suribank.dao.UserDAO;
import com.uos.suribank.dto.UserVO;
import com.uos.suribank.exception.UserNotFoundException;

@RestController
@MapperScan(basePackages = "com.uos.suribank.dao")
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	UserDAO userDAO;

	private String message;
	private HttpStatus status;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserVO vo) {

		try {
			UserVO user = userDAO.login(vo);
			if (user == null) {
				message = "로그인에 실패하였습니다. 계정을 한번더 확인해주세요";
				status = HttpStatus.NO_CONTENT;

				return new ResponseEntity<>(message, status);
			} else {
				// message = "환영합니다 !"
				status = HttpStatus.OK;

				return new ResponseEntity<>(user, status);
			}
		} catch (Exception e) {
			message = "서버 오류가 발생했습니다.";
			status = HttpStatus.INTERNAL_SERVER_ERROR;

			return new ResponseEntity<>(status);
		}
	}

	@PostMapping("/checkId")
	public ResponseEntity<?> checkId(@RequestBody String id) {
		try {
			// 아이디 한번 더 검사
			int checkId = userDAO.checkId(id);

			if (checkId > 0) {
				message = "아이디 중복입니다. 다른 아이디를 선택해주세요";
				status = HttpStatus.BAD_REQUEST;

				return new ResponseEntity<>(status);
			} else {
				message = "아이디를 사용하실 수 있습니다.";
				status = HttpStatus.OK;
				return new ResponseEntity<>(status);
			}

		} catch (Exception e) {
			message = "서버 오류가 발생했습니다.";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<>(status);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserVO vo) {
		// 아이디 한번 더 검사
		int checkId = userDAO.checkId(vo.getId());

		if (checkId > 0) {
			message = "아이디 중복입니다. 다른 아이디를 선택해주세요";
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<>(status);
		} else {
			userDAO.signup(vo);
			status = HttpStatus.OK;
			return new ResponseEntity<>(status);
		}
	}

	//조회
	@GetMapping(path = "/{id}")
	public ResponseEntity<UserVO> getInfo(@PathVariable String id) {

		UserVO user = userDAO.getInfo(id);

		if(user == null){
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		else{
			// message = "환영합니다 !"
			status = HttpStatus.OK;
			return new ResponseEntity<>(user,status);
		}
	}

	//수정
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateInfo(@PathVariable String id, @RequestBody UserVO user){
		user.setId(id);
		int result = userDAO.updateInfo(user);

		if(result == 0){
			throw new UserNotFoundException(String.format("ID[%s] not found", user.getId()));
		}else{
			status = HttpStatus.OK;
			return new ResponseEntity<>(status);
		}
	}

	//삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteInfo(@RequestBody UserVO user){
		int result = userDAO.deleteInfo(user);

		if(result == 0){
			throw new UserNotFoundException(String.format("Delete ID[%s] not found", user.getId()));
		}
		else{
			status = HttpStatus.OK;
			return new ResponseEntity<>(status);
		}
	}

	@GetMapping("/userlist")
	public List<UserVO> userlist(){
		return userDAO.getAllUsers();
	}

	
}
