package me.croco.onulmohaji.place.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.member.service.MemberService;
import me.croco.onulmohaji.place.domain.CustomPlace;
import me.croco.onulmohaji.place.dto.CustomPlaceAddRequest;
import me.croco.onulmohaji.place.dto.CustomPlaceAddResponse;
import me.croco.onulmohaji.place.dto.CustomPlaceListFindResponse;
import me.croco.onulmohaji.place.service.CustomPlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomPlaceController {

    private final CustomPlaceService customPlaceService;
    private final MemberService memberService;

    @PostMapping("/api/customPlace")
    public ResponseEntity<CustomPlaceAddResponse> addCustomPlace(@RequestBody CustomPlaceAddRequest addRequest, HttpServletRequest request) {
        Member loginMember = memberService.getLoginMember(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomPlaceAddResponse(customPlaceService.addCustomPlace(addRequest, loginMember)));
    }

    @GetMapping("/api/customPlace")
    public ResponseEntity<List<CustomPlaceListFindResponse>> findCustomPlaceListByLoginUserId(HttpServletRequest request) {
        Member loginMember = memberService.getLoginMember(request);
        List<CustomPlace> customPlaceList = customPlaceService.findCustomPlaceListByUserId(loginMember.getId());
        return ResponseEntity.ok()
                .body(customPlaceList.stream().map(CustomPlaceListFindResponse::new).toList());
    }
}
