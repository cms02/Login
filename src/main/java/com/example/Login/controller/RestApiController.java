package com.example.Login.controller;

import com.example.Login.entity.Member;
import com.example.Login.entity.Role;
import com.example.Login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
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
        return ResponseEntity.ok("User Joined");
    }

    @GetMapping("/api/v1/user/test")
    public ResponseEntity<String> userAPITest() {
        return ResponseEntity.ok("User Access");
    }

    @GetMapping("/api/v1/admin/test")
    public ResponseEntity<String> adminAPITest() {
        return ResponseEntity.ok("Admin Access");
    }
}
