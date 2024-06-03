package me.croco.onulmohaji.member.controller;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.NaverService;
import me.croco.onulmohaji.member.dto.MemberAddRequest;
import me.croco.onulmohaji.member.dto.NaverLoginRequest;
import me.croco.onulmohaji.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final NaverService naverService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberAddRequest request) {
        Long userId = memberService.saveMember(request);
        return userId != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(userId))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/login/auth/naver")
    public ResponseEntity<NaverLoginRequest> getNaverLoginRequestUrl() {
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);

        String url = naverService.getNaverLoginRequestUrl(state);

        return ResponseEntity.ok()
                .body(new NaverLoginRequest(url, state));
    }

    @GetMapping("/login/auth/naver/token")
    public ResponseEntity<NaverTokenResponse> getAccessTokenNaver(@RequestParam String code, @RequestParam String state) {
        NaverTokenResponse response = naverService.getAccessTokenNaver(code, state);
        return ResponseEntity.ok()
                .body(response);
    }
}

