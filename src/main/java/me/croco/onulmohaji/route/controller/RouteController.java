package me.croco.onulmohaji.route.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.domain.RouteDetail;
import me.croco.onulmohaji.route.dto.*;
import me.croco.onulmohaji.route.service.RouteService;
import me.croco.onulmohaji.util.JsoupCrawling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

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
            List<RouteDetail> routeDetailList = routeService.findRouteDetailListByRouteId(route.getId());

            return ResponseEntity.ok()
                    .body(new RouteFindResponse(route, routeDetailList));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/api/route")
    public void updateRouteDetailOrder(@RequestBody List<RouteDetailUpdateRequest> routeDetailUpdateRequests) {
        routeService.updateRouteDetailOrder(routeDetailUpdateRequests);
    }

}
