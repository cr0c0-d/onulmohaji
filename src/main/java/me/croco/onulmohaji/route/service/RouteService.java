package me.croco.onulmohaji.route.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.member.repository.MemberRepository;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.domain.RouteDetail;
import me.croco.onulmohaji.route.dto.*;
import me.croco.onulmohaji.route.repository.RouteDetailRepository;
import me.croco.onulmohaji.route.repository.RouteRepository;
import me.croco.onulmohaji.util.Authorities;
import me.croco.onulmohaji.util.HttpHeaderChecker;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteDetailRepository routeDetailRepository;
    private final HttpHeaderChecker httpHeaderChecker;
    private final MemberRepository memberRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final PopupstoreRepository popupstoreRepository;
    private final FacilityRepository facilityRepository;

    public Long addRouteDetail(RouteDetailAddRequest addRequest, HttpServletRequest request) {
        Member loginMember = getLoginMember(request);
        Route route = routeRepository.findRouteByDateAndUserId(addRequest.getDate(), loginMember.getId())
                .orElseGet(
                        () -> routeRepository.save(Route.builder()
                                .userId(loginMember.getId())
                                .valid(1)
                                .likeCnt(0)
                                .routeDate(addRequest.getDate())
                                .shareType(0)
                                .build()
                        )
                );

        int maxOrder = routeRepository.findMaxOrderNoByRouteId(route.getId()).orElse(0);

        RouteDetail routeDetail = routeDetailRepository.save(RouteDetail.builder()
                        .orderNo(maxOrder + 1)
                        .placeId(addRequest.getPlaceId())
                        .routeId(route.getId())
                        .placeType(addRequest.getPlaceType())
                .build());

        return routeDetail.getRouteId();
        //return routeRepository.addRouteDetail(addRequest, loginMember);
    }

    public Route findRoute(Long userId, String date, HttpServletRequest request) {
        Route route = routeRepository.findRouteByDateAndUserId(date, userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 route"));
        Member loginMember = getLoginMember(request);
        boolean isAdmin = loginMember.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch((authority -> authority.equals(Authorities.ROLE_ADMIN.getAuthorityName())));
        switch (route.getShareType()) {

            case 0 :    // 비공개
                // 로그인 사용자의 route가 아니고, 로그인 사용자가 관리자가 아님
                if(!route.getUserId().equals(loginMember.getId())
                        && !isAdmin) {
                    throw new AccessDeniedException("조회 권한 없음");
                }
                break;

            case 1 :    // 제한 공유


            case 2 :    // 완전 공유

        }
        return route;
    }

    public List<RouteDetailFindResponse> findRouteDetailListByRouteId(Long routeId) {
        List<RouteDetail> routeDetailList = routeDetailRepository.findByRouteIdOrderByOrderNo(routeId);
        return routeDetailList.stream().map(routeDetail -> {
            switch (routeDetail.getPlaceType()) {
                case "exhibition" :
                    return new RouteDetailFindResponse(routeDetail, exhibitionRepository.findById(routeDetail.getPlaceId()).get());

                case "popup" :
                    return new RouteDetailFindResponse(routeDetail, popupstoreRepository.findById(routeDetail.getPlaceId()).get());

                default:
                    return new RouteDetailFindResponse(routeDetail, facilityRepository.findById(routeDetail.getPlaceId()).get());
            }
        }).toList();
    }

    public void updateRouteDetailOrder(List<RouteDetailUpdateRequest> routeDetailUpdateRequests) {
        routeRepository.updateRouteDetailOrder(routeDetailUpdateRequests);
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
