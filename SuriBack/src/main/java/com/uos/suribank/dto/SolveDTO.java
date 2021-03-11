package com.uos.suribank.dto;

import java.util.List;

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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class solveInfoDTO{
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
        List<solveInfoDTO> solvedInfo;
        int totalElements;
        int numberofElements;
        int totalPages;
        int page;
        int size;
        String sort;
    }

}
