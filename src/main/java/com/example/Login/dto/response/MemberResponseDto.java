package com.example.Login.dto.response;


import lombok.Builder;
import lombok.Data;

public class MemberResponseDto {

    @Data
    @Builder
    public static class Login {
        private String username;
        private String message;
        private String accessToken;
        private String refreshToken;
    }
}
