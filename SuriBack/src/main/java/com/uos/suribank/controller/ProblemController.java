package com.uos.suribank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.uos.suribank.dto.SubjectDTO;
import com.uos.suribank.dto.ProblemDTO.problemAddDTO;
import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.exception.InsertErrorException;
import com.uos.suribank.exception.NotFoundException;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.service.ProblemService;

@CrossOrigin(origins = "*")
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

    //과목-코드 리스트 받아오기
    @GetMapping(path = "/subjectList")
    public ResponseEntity<?> getSubjectList(){
        List<SubjectDTO> sList = problemService.getSubjectList();

        if(sList == null){
            throw new NotFoundException("Subject not found");
		}
        return ResponseEntity.ok(sList);
    }

    //삽입
    @PutMapping(path = "/add")
    public void addProblem(@RequestBody problemAddDTO pAddDTO){
        boolean result = false;
        try{
            result = problemService.addProblem(pAddDTO);
        }catch(Exception e){
            throw new InsertErrorException("Failed to Upload Images");
        }
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
