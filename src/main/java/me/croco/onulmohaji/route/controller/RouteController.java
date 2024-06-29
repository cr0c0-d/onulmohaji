package me.croco.onulmohaji.route.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.member.service.MemberService;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.domain.RoutePermission;
import me.croco.onulmohaji.route.dto.*;
import me.croco.onulmohaji.route.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final MemberService memberService;

    @PostMapping("/api/routeDetail")
    public ResponseEntity<Long> addRouteDetail(@RequestBody RouteDetailAddRequest addRequest, HttpServletRequest request) {
        Member loginMember = memberService.getLoginMember(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routeService.addRouteDetail(addRequest, loginMember));
    }

    @GetMapping("/api/route")
    public ResponseEntity<RouteFindResponse> findRoute(@RequestParam Long userId, @RequestParam String date, HttpServletRequest request) {
        try {
            Route route = routeService.findRoute(userId, date, request);
            List<RouteDetailFindResponse> routeDetailList = routeService.findRouteDetailListByRouteId(route.getId());
            List<String> routeMapUrlList = routeService.getRouteMapUrlList(routeDetailList);
            return ResponseEntity.ok()
                    .body(new RouteFindResponse(route, routeDetailList, routeMapUrlList));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/api/route")
    public void updateRouteDetailOrder(@RequestBody List<RouteDetailUpdateRequest> routeDetailUpdateRequests) {
        routeService.updateRouteDetailOrder(routeDetailUpdateRequests);
    }

    @DeleteMapping("/api/routeDetail/{routeDetailId}")
    public void deleteRouteDetail(@PathVariable Long routeDetailId) {
        routeService.deleteRouteDetail(routeDetailId);
    }

    @GetMapping("/api/route/permission/url/{routeId}")
    public ResponseEntity<String> getRoutePermissionUrl(@PathVariable Long routeId, HttpServletRequest request) {
        Member loginMember = memberService.getLoginMember(request);

        String routePermissionUrl = routeService.getRoutePermissionUrl(routeId, loginMember);
        return ResponseEntity.ok()
                .body(routePermissionUrl);
    }

    @GetMapping("/api/route/permission/{shareCode}")
    public ResponseEntity<RoutePermissionInfoResponse> getRouteInfoByShareCode(@PathVariable String shareCode) {
        try {
            return ResponseEntity.ok()
                    .body(routeService.getRouteInfoByShareCode(shareCode));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/api/route/permission")
    public ResponseEntity<RoutePermission> addRoutePermission(@RequestBody RoutePermissionAddRequest request) {
        RoutePermission routePermission = routeService.addRoutePermission(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routePermission);
    }

    @GetMapping("/api/route/dateList/{userId}")
    public ResponseEntity<List<String>> findRouteDateList(@PathVariable Long userId) {
        List<Route> routeList = routeService.findRouteDateList(userId);
        List<String> routeDateList = routeList.stream().map(Route::getRouteDate).toList();

        return ResponseEntity.ok()
                .body(routeDateList);

    }

}
