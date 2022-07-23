package com.example.Login.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.Login.dto.request.MemberRequestDto;
import com.example.Login.entity.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtil {

    public static String generateToken(Member member) {

        return JWT.create()
                .withSubject("jwt")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("username", member.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public static String verifyToken(HttpServletRequest request) {
        return null;
    }




}
