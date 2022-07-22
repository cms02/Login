package com.example.Login.dto.request;

import lombok.Data;

public class MemberRequestDto {

    @Data
    public static class Login {
        private String username;
        private String password;
    }

}
