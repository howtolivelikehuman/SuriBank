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
    public solveTableDTO getUserSolvedList(PageableDTO page, Long userId, Long problemId){
        Pageable pageable = ProblemPageable.makePageable(page);
        return solveRepository.getSolvedAnswerList(pageable, 1, userId, problemId);
    }

    public solveTableDTO getProblemSolvedList(PageableDTO page, Long userId, Long problemId){
        Pageable pageable = ProblemPageable.makePageable(page);
        return solveRepository.getSolvedAnswerList(pageable, 2, userId, problemId);
    }


    @Transactional
    public void solve(Long user_id, Long problem_id, solveProblemDTO sPDTO){
        solveRepository.insertAnswer(user_id, problem_id, sPDTO);
        //점수 입력
        insertScore(problem_id, sPDTO.getScore());
    }

    //수정 -> 점수 줬던 것도 롤백
    @Transactional
    public void update(Long user_id, Long problem_id, solveProblemDTO sPDTO){
        float lastScore = solveRepository.findAnswer(user_id,problem_id).getScore();
        solveRepository.updateAnswer(user_id, problem_id, sPDTO);
        updateScore(problem_id,sPDTO.getScore(), lastScore);
    }

    public void updateScore(Long id, float score, float lastScore){
        problemShortDTO psdto = problemRepository.getScoreAndHit(id);
        int nhit = psdto.getHit();
        float nscore = ((nhit) * psdto.getScore() - lastScore + score)/(nhit);
        problemRepository.updateScore(id,nhit,nscore);
    }

    public void insertScore(Long id, float score){
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
    public solveDTO findAnswer(Long user_id, Long problem_id){
        return solveRepository.findAnswer(user_id, problem_id);
    }
    
}
