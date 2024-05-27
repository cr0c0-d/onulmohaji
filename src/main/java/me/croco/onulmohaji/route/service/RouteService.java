package me.croco.onulmohaji.route.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.KakaoLocalService;
import me.croco.onulmohaji.exhibition.domain.Exhibition;
import me.croco.onulmohaji.exhibition.repository.ExhibitionRepository;
import me.croco.onulmohaji.facility.domain.Facility;
import me.croco.onulmohaji.facility.repository.FacilityRepository;
import me.croco.onulmohaji.festival.repository.FestivalRepository;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.member.repository.MemberRepository;
import me.croco.onulmohaji.popupstore.domain.Popupstore;
import me.croco.onulmohaji.popupstore.repository.PopupstoreRepository;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.domain.RouteDetail;
import me.croco.onulmohaji.route.dto.RouteDetailAddRequest;
import me.croco.onulmohaji.route.dto.RouteDetailFindResponse;
import me.croco.onulmohaji.route.dto.RouteDetailUpdateRequest;
import me.croco.onulmohaji.route.dto.RouteMapUrlFindRequest;
import me.croco.onulmohaji.route.repository.RouteDetailRepository;
import me.croco.onulmohaji.route.repository.RouteRepository;
import me.croco.onulmohaji.util.Authorities;
import me.croco.onulmohaji.util.HttpHeaderChecker;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final FestivalRepository festivalRepository;
    private final KakaoLocalService kakaoLocalService;

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
                    return new RouteDetailFindResponse(routeDetail, exhibitionRepository.findById(Long.parseLong(routeDetail.getPlaceId())).get());

                case "popup" :
                    return new RouteDetailFindResponse(routeDetail, popupstoreRepository.findById(Long.parseLong(routeDetail.getPlaceId())).get());

                case "festival" :
                    return new RouteDetailFindResponse(routeDetail, festivalRepository.findById(routeDetail.getPlaceId()).get());

                default:
                    return new RouteDetailFindResponse(routeDetail, facilityRepository.findById(Long.parseLong(routeDetail.getPlaceId())).get());
            }
        }).toList();
    }

    public List<String> getRouteMapUrlList(List<RouteDetailFindResponse> routeDetailList) {
        List<String> urlList = new ArrayList<>();

        for (int i = 0 ; i < routeDetailList.size() - 1; i++) {
            RouteDetailFindResponse thisRoute = routeDetailList.get(i);
            RouteDetailFindResponse nextRoute = routeDetailList.get(i+1);
            urlList.add("https://map.kakao.com/?map_type=TYPE_MAP&target=walk&rt=" +
                    thisRoute.getWpointx()+","+thisRoute.getWpointy()+","+nextRoute.getWpointx()+","+nextRoute.getWpointy()+
                    "&rt1="+thisRoute.getPlaceName().replace(" ", "+") +
                    "&rt2=" + nextRoute.getPlaceName().replace(" ", "+"));

        }
        return urlList;
    }

    public void updateRouteDetailOrder(List<RouteDetailUpdateRequest> routeDetailUpdateRequests) {
        routeRepository.updateRouteDetailOrder(routeDetailUpdateRequests);
    }
    public void deleteRouteDetail(Long routeDetailId) {
        RouteDetail routeDetail = routeDetailRepository.findById(routeDetailId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 routeDetail"));
        routeDetailRepository.deleteById(routeDetailId);
        List<RouteDetail> routeDetailList = routeDetailRepository.findByRouteIdOrderByOrderNo(routeDetail.getRouteId());
        if(routeDetailList == null || routeDetailList.isEmpty()) {
            routeRepository.deleteById(routeDetail.getRouteId());
        } else {
            AtomicInteger index = new AtomicInteger(1);
            routeDetailList.forEach(detail -> {
                detail.setOrderNo(index.getAndIncrement());
            });
            routeDetailRepository.saveAll(routeDetailList);
        }
    }
    public Member getLoginMember(HttpServletRequest request) {
        // 로그인 상태인지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            boolean validToken = httpHeaderChecker.checkAuthorizationHeader(request);

            if (!validToken) {   // 비로그인 상태
                return null;
            }
        }

        // 로그인 멤버 반환
        return memberRepository.findByEmail(authentication.getName()).get();
    }
}
