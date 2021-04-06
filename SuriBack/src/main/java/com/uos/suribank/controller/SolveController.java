package com.uos.suribank.controller;

import com.uos.suribank.dto.SolveDTO.solveDTO;
import com.uos.suribank.dto.SolveDTO.solveProblemDTO;
import com.uos.suribank.dto.SolveDTO.solveTableDTO;
import com.uos.suribank.exception.InsertErrorException;
import com.uos.suribank.exception.NotFoundException;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.service.SolveService;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/solve")
public class SolveController {
    
    @Autowired
    private SolveService solveService;


    //문제 풀기
    @PostMapping(value = "/{problem_id}")
    public void solveProblem(Authentication authentication,@PathVariable Long problem_id, @RequestBody solveProblemDTO solveproblemdto){
        Long user_id = Long.parseLong(authentication.getName());
        try{
            solveService.solve(user_id, problem_id, solveproblemdto);
        }catch(Exception e){
            e.printStackTrace();
            throw new InsertErrorException("Failed to Insert Answer");
        }
    }

    //정답 입력 여부 확인
    @GetMapping(value = "/check/{problem_id}")
    public ResponseEntity<solveDTO> checkProblem(Authentication authentication,@PathVariable Long problem_id){
        Long user_id = Long.parseLong(authentication.getName());
        solveDTO answer = null;
        try{
            answer =solveService.findAnswer(user_id, problem_id);
            if(answer == null){
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new InsertErrorException("Failed to Check Answer");
        }
        return ResponseEntity.ok(answer);
    }
    
    //세세 정답 확인
    @GetMapping(value = "/{solve_id}")
    public ResponseEntity<solveDTO> getAnswer(@PathVariable Long solve_id){
        solveDTO answer = null;
        try{
            answer = solveService.getAnswer(solve_id);
            if(answer == null){
                throw new NotFoundException("Cannot find answer for id =" + solve_id);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new NotFoundException("Cannot find answer for id =" + solve_id);
        }
        return ResponseEntity.ok(answer);
    }


    //User가 푼 답
    @GetMapping(path = "/list/user/{user_id}")
    public ResponseEntity<?> getUserSolvedList(@ModelAttribute PageableDTO page, @PathVariable("user_id") Long userId){
        solveTableDTO stableDTO  = solveService.getUserSolvedList(page,userId, -1l);
        if(stableDTO == null){
			throw new NotFoundException("Page not found");
		}
        return ResponseEntity.ok(stableDTO);
    }


   
    //Problem에 따른 풀이
    @GetMapping(path = "/list/problem/{problem_id}")
    public ResponseEntity<?> getProblemSolvedList(Authentication authentication,
                                                  @ModelAttribute PageableDTO page, @PathVariable("problem_id") Long problemId){

        solveTableDTO stableDTO  = null;
        try {
            stableDTO = solveService.getProblemSolvedList(page, Long.parseLong(authentication.getName()), problemId);
            if(stableDTO == null){
                throw new NotFoundException("Page not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(stableDTO);
    }
}
