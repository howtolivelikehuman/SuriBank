package com.uos.suribank.service;


import com.uos.suribank.dto.SolveDTO.solveDTO;
import com.uos.suribank.dto.SolveDTO.solveProblemDTO;
import com.uos.suribank.repository.SolveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolveService {

    @Autowired
    private SolveRepository solveRepository;

    @Transactional
    public void solve(Long user_id, solveProblemDTO sPDTO){

        //최초 정답 입력
        if(solveRepository.getAnswer(user_id, sPDTO.getProblem()) == null){
            solveRepository.insertAnswer(user_id, sPDTO);
        }
        else{ //수정
            solveRepository.updateAnswer(user_id, sPDTO);
        }
    }

    //풀이 보기
    public solveDTO getAnswer(Long user_id, Long problem_id){
        return solveRepository.getAnswer(user_id, problem_id);
    }
    
}
