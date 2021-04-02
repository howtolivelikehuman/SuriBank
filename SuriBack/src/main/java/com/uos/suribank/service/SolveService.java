package com.uos.suribank.service;


import com.uos.suribank.dto.ProblemDTO.problemShortDTO;
import com.uos.suribank.dto.SolveDTO.solveDTO;
import com.uos.suribank.dto.SolveDTO.solveProblemDTO;
import com.uos.suribank.dto.SolveDTO.solveTableDTO;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.pagination.ProblemPageable;
import com.uos.suribank.repository.ProblemReopository;
import com.uos.suribank.repository.SolveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolveService {

    @Autowired
    private SolveRepository solveRepository;
    @Autowired
    private ProblemReopository problemRepository;

    //문제풀이
    public solveTableDTO getUserSolvedList(PageableDTO page, Long id){
        Pageable pageable = ProblemPageable.makePageable(page);
        return solveRepository.getSolvedAnswerList(pageable, 1, id);
    }

    public solveTableDTO getProblemSolvedList(PageableDTO page, Long id){
        Pageable pageable = ProblemPageable.makePageable(page);
        return solveRepository.getSolvedAnswerList(pageable, 2, id);
    }


    @Transactional
    public void solve(Long user_id, Long problem_id, solveProblemDTO sPDTO){

        //최초 정답 입력
        if(findAnswer(user_id, problem_id) == null){
            solveRepository.insertAnswer(user_id, problem_id, sPDTO);
        }
        else{ //수정
            solveRepository.updateAnswer(user_id, problem_id, sPDTO);
        }
        //점수 입력
        scoreProblem(problem_id, sPDTO.getScore());
    }

    public void scoreProblem(Long id, int score){
        problemShortDTO psdto = problemRepository.getScoreAndHit(id);
        int nhit = psdto.getHit()+1;
        float nscore = ((nhit-1) * psdto.getScore() + score)/(nhit);
        
        problemRepository.updateScore(id, nhit, nscore);
    }

    //풀이 보기
    public solveDTO getAnswer(Long solve_id){
        return solveRepository.getAnswer(solve_id);
    }

    //풀었는지 확인
    public Long findAnswer(Long user_id, Long problem_id){
        return solveRepository.findAnswer(user_id, problem_id);
    }
    
}
