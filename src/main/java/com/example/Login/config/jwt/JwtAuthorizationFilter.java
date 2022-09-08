package com.example.Login.config.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.Login.config.auth.PrincipalDetails;
import com.example.Login.entity.Member;
import com.example.Login.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    /*인가 처리 관련 로직*/

    private final MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthorizationFilter 진입");
        /*header 값 체크  */
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            /*토큰 검증*/
            String username = TokenUtil.verifyToken(request);

            if (username != null) {
                Member member = memberRepository.findByUsername(username);

                PrincipalDetails principalDetails = new PrincipalDetails(member);

                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

                /*SecurityContext에 직접 접근 후 세션 생성 -> 자동으로 UserDetailsService에 있는 loadByUsername 호출*/
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            log.error("{}",e.getMessage());

        }





    }
}
