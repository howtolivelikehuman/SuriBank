package com.uos.suribank.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
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

        public problemShortDTO(int hit, float score){
            this.score = score;
            this.hit = hit;
        }
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
        List<MultipartFile> a_img;
        List<MultipartFile> q_img;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemAddinfoDTO{
        private Long uploader_id;
        String title;
        Long subject;
        int type;
        String professor;
        String question;
        String answer;
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
        //List<problemImageDTO> images;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemImageDTO{
        private Long id;
        Long problemTable;
        int type;
        String path;
    }
}
