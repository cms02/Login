package com.example.Login.controller;

import com.example.Login.entity.Member;
import com.example.Login.entity.Role;
import com.example.Login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public ResponseEntity<String> join(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole(Role.USER);
        memberRepository.save(member);
        return ResponseEntity.ok("회원가입 완료");
    }
}
