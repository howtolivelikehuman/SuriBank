package com.uos.suribank.dto;

import java.util.List;

import org.hibernate.annotations.Sort;

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
        String explanation;
        float score;
        int hit;
    
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemTableDTO{
        List<problemInfoDTO> probleminfo;
        Long totalElements;
        int totalPages;
        int size;
        int page;
        int numberofElements;
        String sort;
    }
}
