package com.example.Login.config.jwt;

import com.example.Login.config.auth.PrincipalDetails;
import com.example.Login.dto.request.MemberRequestDto;
import com.example.Login.dto.response.MemberResponseDto;
import com.example.Login.dto.response.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        /*  /login[POST] 요청 시 진입*/
        log.info("JwtAuthenticationFilter 진입");

        MemberRequestDto.Login loginDto = new MemberRequestDto.Login();
        try {
            /*입력한 아이디, 패스워드 파싱 */
            loginDto = objectMapper.readValue(request.getInputStream(), MemberRequestDto.Login.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*UsernamePassword 로 토큰 생성*/
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        /* authenticate() 호출 시,  UserDetailsService 의 loadUserByUsername 호출 후
        *  UserDetails 리턴 받아서 토큰의 두번째 파라미터 값인 credential 과 UserDetails의 password 값 비교 후
        *  Authentication 객체 만들어서 필터 체인으로 Return*/
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("Authentication: " + principalDetails.getUsername());

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        /*JWT Token(ACCESS, REFRESH) 생성*/
        String accessToken = TokenUtil.generateAccessToken(principalDetails.getMember());
        String refreshToken = TokenUtil.generateRefreshToken();

        /*Login 성공 Response 담기*/
        MemberResponseDto.Login memberResponseDto = MemberResponseDto.Login.builder()
                .username(principalDetails.getUsername())
                .message("LOGIN SUCCESS")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        ResponseDto responseDto = ResponseDto.builder()
                .data(memberResponseDto)
                .status(HttpStatus.OK)
                .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));

    }
}
