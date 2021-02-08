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
        String professor;
        String uploader;
        int type; //0 for 
        float score;
        int hit;

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemTableDTO{
        List<problemInfoDTO> probleminfo;
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
