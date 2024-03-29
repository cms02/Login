package com.example.Login.controller;

import com.example.Login.dto.request.MemberRequestDto;
import com.example.Login.dto.response.ResponseDto;
import com.example.Login.entity.Member;
import com.example.Login.entity.Role;
import com.example.Login.repository.MemberRepository;
import com.example.Login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole(Role.USER);
        memberRepository.save(member);
        return ResponseEntity.ok("User Joined");
    }

    @PostMapping("/reissue")
    public ResponseEntity<ResponseDto> reissue(@RequestBody MemberRequestDto.Reissue reissue) {
        return userService.reissue(reissue);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody MemberRequestDto.Logout logout) {
        return userService.logout(logout);
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
