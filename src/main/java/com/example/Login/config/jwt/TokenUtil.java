package com.example.Login.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class TokenUtil {

    private static int EXPIRATION_TIME;

    public static String generateToken(String username,TokenType tokenType) {

        if (tokenType.equals(TokenType.ACCESS)) {
            EXPIRATION_TIME = JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME;
        } else {
            EXPIRATION_TIME = JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME;
        }

        return JWT.create()
                .withSubject("jwt")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("username", username)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public static String verifyToken(String jwtToken) {
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
    }




}
