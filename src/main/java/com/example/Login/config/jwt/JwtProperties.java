package com.example.Login.config.jwt;

public interface JwtProperties {
    String SECRET = "jWtSeCrEtKeY";
    int ACCESS_TOKEN_EXPIRATION_TIME = 1000*60*5; //5mins
    int REFRESH_TOKEN_EXPIRATION_TIME = 1000*60*60*24*7; //7days
    String TOKEN_PREFIX = "Bearer ";
    String ACCESS_HEADER_STRING = "Authorization";
    String REFRESH_HEADER_STRING = "RefreshToken";
}
