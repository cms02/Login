package com.example.Login.service;

import com.example.Login.config.jwt.JwtProperties;
import com.example.Login.config.jwt.TokenType;
import com.example.Login.config.jwt.TokenUtil;
import com.example.Login.config.redis.RedisService;
import com.example.Login.dto.request.MemberRequestDto;
import com.example.Login.dto.response.MemberResponseDto;
import com.example.Login.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final RedisService redisService;

    public ResponseEntity<ResponseDto> reissue(MemberRequestDto.Reissue reissue) {
        String username = TokenUtil.verifyToken(reissue.getRefreshToken());
        if (username == null) {
            ResponseDto responseDto = ResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Invalid Refresh Token")
                    .build();
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        username = TokenUtil.verifyToken(reissue.getAccessToken());

        String redisToken = redisService.getValues(username);

        if (!redisToken.isEmpty()) {
            String newAccessToken = TokenUtil.generateToken(username, TokenType.ACCESS);
            String newRefreshToken = TokenUtil.generateToken(username, TokenType.REFRESH);

            MemberResponseDto.TokenInfo tokenInfo = MemberResponseDto.TokenInfo.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();

            ResponseDto responseDto = ResponseDto.builder()
                    .data(tokenInfo)
                    .status(HttpStatus.OK)
                    .build();

            redisService.setValues(username, newRefreshToken, JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid Refresh Token")
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> logout(MemberRequestDto.Logout logout) {
        String username = TokenUtil.verifyToken(logout.getAccessToken());
        redisService.deleteValues(username);
        return ResponseEntity.ok("Logout Success");
    }
}
