package me.croco.onulmohaji.member.controller;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.member.dto.MemberAddRequest;
import me.croco.onulmohaji.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberAddRequest request) {
        Long userId = memberService.saveMember(request);
        return userId != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(userId))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
