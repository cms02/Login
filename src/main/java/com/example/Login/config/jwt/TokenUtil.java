package com.example.Login.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.Login.dto.request.MemberRequestDto;
import com.example.Login.entity.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtil {

    private static int EXPIRATION_TIME;
    private static String ACCESS_TOKEN;
    private static String REFRESH_TOKEN;

    public static String generateAccessToken(Member member) {

        EXPIRATION_TIME = JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME;
        ACCESS_TOKEN = JwtProperties.TOKEN_PREFIX +
                        JWT.create()
                        .withSubject("jwt")
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .withClaim("username", member.getUsername())
                        .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        return ACCESS_TOKEN;
    }

    public static String generateRefreshToken() {

        EXPIRATION_TIME = JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME;
        REFRESH_TOKEN = JwtProperties.TOKEN_PREFIX +
                        JWT.create()
                        .withSubject("jwt")
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        return REFRESH_TOKEN;
    }

    public static String verifyToken(HttpServletRequest request) {

        String jwtToken = request.getHeader(JwtProperties.ACCESS_HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
    }




}
