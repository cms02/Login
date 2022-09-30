package com.example.Login.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDto<T> {
    private T data;
    private HttpStatus status;
    private String message;

    @Builder
    public ResponseDto(T data, HttpStatus status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }
}
