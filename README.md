# Login
SpringSecurity, JWT, JPA, Redis 를 이용하여 만든 로그인 프로젝트 입니다.

# ⚙개발 환경
- Language : Java 11
- Framework : Springboot 2.7.1
- IDE : IntelliJ
- Database : H2, Redis

# 🕹기능
- SpringSecurity 환경에서 로그인 시 클라이언트에 access token과 refresh token을 발급 (refresh token은 redis에 저장)
- 클라이언트에서 access token을 header에 담아 API 통신
- access token이 만료 되었을 시 refresh token을 담아서 재전송
- refresh token이 유효하다면 access token 재발급


