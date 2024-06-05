package me.croco.onulmohaji.route.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.exhibition.domain.Exhibition;
import me.croco.onulmohaji.exhibition.domain.ExhibitionDetail;
import me.croco.onulmohaji.exhibition.repository.ExhibitionDetailRepository;
import me.croco.onulmohaji.exhibition.repository.ExhibitionRepository;
import me.croco.onulmohaji.popupstore.domain.Popupstore;
import me.croco.onulmohaji.popupstore.repository.PopupstoreRepository;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.domain.RouteDetail;
import me.croco.onulmohaji.route.dto.*;
import me.croco.onulmohaji.route.service.RouteService;
import me.croco.onulmohaji.util.JsoupCrawling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping("/api/routeDetail")
    public ResponseEntity<Long> addRouteDetail(@RequestBody RouteDetailAddRequest addRequest, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routeService.addRouteDetail(addRequest, request));
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
        String routePermissionUrl = routeService.getRoutePermissionUrl(routeId, request);
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

}
