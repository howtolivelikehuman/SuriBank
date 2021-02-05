package com.uos.suribank.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProblemDTO{

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class problemInfoDTO{
        private Long no;
        String title;
        String subject;
        String uploader;
        float score;
        int hit;
    }
}
