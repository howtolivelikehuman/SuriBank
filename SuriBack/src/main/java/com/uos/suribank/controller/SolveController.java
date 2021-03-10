package com.uos.suribank.controller;

import com.uos.suribank.dto.SolveDTO.solveDTO;
import com.uos.suribank.dto.SolveDTO.solveProblemDTO;
import com.uos.suribank.exception.InsertErrorException;
import com.uos.suribank.exception.NotFoundException;
import com.uos.suribank.service.SolveService;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping(value = "/{problem_no}")
    public void solveProblem(Authentication authentication, @RequestBody solveProblemDTO solveproblemdto){
        Long user_id = Long.parseLong(authentication.getName());
        try{
            solveService.solve(user_id, solveproblemdto);
        }catch(Exception e){
            e.printStackTrace();
            throw new InsertErrorException("Failed to Insert Answer");
        }
    } 

    @GetMapping(value = "/{problem_id}/{user_id}")
    public ResponseEntity<solveDTO> getAnswer(@PathVariable Long user_id, @PathVariable Long problem_id){
        solveDTO answer;

        try{
            System.out.println(user_id + " " + problem_id);
            answer =  solveService.getAnswer(user_id, problem_id);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new NotFoundException("Cannot find answer for id =" + user_id + " problem = " + problem_id);
        }
        return ResponseEntity.ok(answer);
    }



}
