package com.example.Login.config.jwt;

public interface JwtProperties {
    String SECRET = "jWtSeCrEtKeY";
    int EXPIRATION_TIME = 1000*60*5; //30mins
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
