package com.uos.suribank.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SolveDTO {
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class solveProblemDTO{
        String userAnswer;
        float score;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class solveDTO{
        private Long id;
        Long problem_id;
        Long user_id;
        String userName;
        String userAnswer;
        float score;
        Date solveDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class solveUserInfoDTO{
        private Long id;
        Long problem_id;
        String problemName;
        Long user_id;
        String userName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class solveTableDTO{
        List<?> solvedInfo;
        int totalElements;
        int numberofElements;
        int totalPages;
        int page;
        int size;
        String sort;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class solveProblemInfoDTO{
        private Long id;
        Long user_id;
        String userName;
        String userAnswer;
        Date solveDate;
    }
}
