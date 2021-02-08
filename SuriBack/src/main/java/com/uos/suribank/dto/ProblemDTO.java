package com.uos.suribank.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProblemDTO{

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemInfoDTO{
        private Long id;
        String title;
        String subject;
        String uploader;
        String professor;
        float score;
        int hit;
        int type; //0 for 
    
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemTableDTO{
        List<problemInfoDTO> probleminfo;
        Long totalElements;
        int numberofElements;
        int totalPages;
        int page;
        int size;
        String sort;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemAddDTO{
        private Long uploader_id;
        String title;
        String subject;
        int type;
        String professor;
        String question;
        String answer;
    }
}
