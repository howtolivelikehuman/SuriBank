package com.uos.suribank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.exception.UserNotFoundException;
import com.uos.suribank.service.ProblemService;

@RestController
@RequestMapping(value = "/api/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    // 조회
    @GetMapping(path = "/list")
    public ResponseEntity<?> getList(Pageable pageable) {

        problemTableDTO pDto = problemService.getPage(pageable);
        if(pDto == null){
			throw new UserNotFoundException("Page not found");
		}
        return ResponseEntity.ok(pDto);
    }
}
