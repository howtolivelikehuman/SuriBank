package com.uos.suribank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SolveDTO {
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class solveProblemDTO{
        Long problem_id;
        String userAnswer;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class solveDTO{
        private Long id;
        Long problem_id;
        Long user_id;
        String userAnswer;
    }

}
