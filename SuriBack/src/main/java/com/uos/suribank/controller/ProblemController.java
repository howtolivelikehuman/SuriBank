package com.uos.suribank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.suribank.dto.ProblemDTO.problemAddDTO;
import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.exception.InsertErrorException;
import com.uos.suribank.exception.NotFoundException;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.service.ProblemService;

@RestController
@RequestMapping(value = "/api/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    //목록 조회
    @GetMapping(path = "/list")
    public ResponseEntity<?> getList(@RequestBody PageableDTO pageableDTO) {
        
        problemTableDTO pDto = problemService.getProblemList(pageableDTO);

        if(pDto == null){
			throw new NotFoundException("Page not found");
		}
        return ResponseEntity.ok(pDto);
    }

    //삽입
    @PutMapping(path = "/add")
    public void addProblem(@RequestBody problemAddDTO pAddDTO){
        boolean result = problemService.addProblem(pAddDTO);

        if(!result){
            throw new InsertErrorException("Failed to insert into DB");
        }
    }

    //상세조회
    @GetMapping(path = "/{id}")
    public ResponseEntity<problemInfoDTO> getInfo(@PathVariable Long id){
        problemInfoDTO pIDTO = problemService.getProblemInfo(id);

        if(pIDTO == null){
			throw new NotFoundException("Page not found");
		}
        return ResponseEntity.ok(pIDTO);
    }
}
