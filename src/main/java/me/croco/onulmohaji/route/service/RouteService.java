package me.croco.onulmohaji.route.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.member.repository.MemberRepository;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.domain.RouteDetail;
import me.croco.onulmohaji.route.dto.RouteDetailAddRequest;
import me.croco.onulmohaji.route.repository.RouteDetailRepository;
import me.croco.onulmohaji.route.repository.RouteRepository;
import me.croco.onulmohaji.util.HttpHeaderChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteDetailRepository routeDetailRepository;
    private final HttpHeaderChecker httpHeaderChecker;
    private final MemberRepository memberRepository;

    public Long addRouteDetail(RouteDetailAddRequest addRequest, HttpServletRequest request) {
        Member loginMember = getLoginMember(request);
        Long routeId = routeRepository.findRouteIdByDateAndUserId(addRequest.getDate(), loginMember.getId())
                .orElse(
                        routeRepository.save(Route.builder()
                                        .userId(loginMember.getId())
                                        .valid(1)
                                        .likeCnt(0)
                                        .routeDate(LocalDate.parse(addRequest.getDate()))
                                        .shareType(0)
                                        .build()
                                )
                                .getId()
                );
        int maxOrder = routeRepository.findMaxOrderNoByRouteId(routeId).orElse(0);

        RouteDetail routeDetail = routeDetailRepository.save(RouteDetail.builder()
                        .orderNo(maxOrder + 1)
                        .placeId(addRequest.getPlaceId())
                        .routeId(routeId)
                        .placeType(addRequest.getPlaceType())
                .build());

        return routeDetail.getRouteId();
        //return routeRepository.addRouteDetail(addRequest, loginMember);
    }

    public Member getLoginMember(HttpServletRequest request) {
        // 로그인 상태인지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            boolean validToken = httpHeaderChecker.checkAuthorizationHeader(request);

            if (!validToken) {   // 비로그인 상태
                return null;
            }
        }

        // 로그인 멤버 반환
        return memberRepository.findByEmail(authentication.getName()).get();
    }

}
