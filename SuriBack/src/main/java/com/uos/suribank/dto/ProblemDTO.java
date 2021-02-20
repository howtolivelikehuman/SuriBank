package com.uos.suribank.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProblemDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemShortDTO{
        private Long id;
        String title;
        Long subject;
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
        List<problemShortDTO> probleminfo;
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
        Long subject;
        int type;
        String professor;
        String question;
        String answer;
        MultipartFile[] q_img;
        MultipartFile[] a_img;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemInfoDTO{
        private Long id;
        String title;
        Long subject;
        String professor;
        String question;
        String answer;
        String uploader;
        Date registerdate;
        int type; //0 for 
        float score;
        int hit;
        List<String> images;
    }
}
