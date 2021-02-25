package com.uos.suribank.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UserDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class loginDTO{
        String email;
        String password;
    }

    @Data
    @Builder
    public static class usertokenDTO{
        private Long id;
        List<String> type;
        String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    //@JsonFilter("userInfo")
    public static class infoDTO{
        private Long id;
        String email;
        String name;
        String nickname;
        String major;
        List<String> type;
        Date registerdate;
        int point;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class signupDTO{
        
        @Size(min=4, max=30, message = "id는 4~20자로 입력해야 합니다.")
        String email;

        //@Size(min=8, max=20, message = "password는 8~20자로 입력해야 합니다.")
        String password;

        @Size(min=2, max=8, message = "이름은 2~8자로 입력해야 합니다.")
        String name;

        @Size(min=1, max=20, message = "별명은 1~20자로 입력해야 합니다.")
        String nickname;
        String major;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateDTO{
        //@Size(min=8, max=20, message = "password는 8~20자로 입력해야 합니다.")
        String password;

        @Size(min=2, max=8, message = "이름은 2~8자로 입력해야 합니다.")
        String name;

        @Size(min=1, max=20, message = "별명은 1~20자로 입력해야 합니다.")
        String nickname;
        
        String major;
    }
}