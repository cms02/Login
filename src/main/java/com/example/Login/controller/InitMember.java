package com.example.Login.controller;

import com.example.Login.entity.Member;
import com.example.Login.entity.Role;
import com.example.Login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitMemberService {

        private final MemberRepository memberRepository;
        private final PasswordEncoder passwordEncoder;

        @Transactional
        public void init() {
            String encPwd1 = passwordEncoder.encode("password1");
            Member member1 = new Member("username1", encPwd1, 10, Role.USER);

            String encPwd2 = passwordEncoder.encode("password2");
            Member member2 = new Member("username2", encPwd2, 20, Role.ADMIN);

            memberRepository.save(member1);
            memberRepository.save(member2);

        }

    }

}
