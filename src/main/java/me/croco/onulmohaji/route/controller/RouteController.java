package me.croco.onulmohaji.route.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.route.domain.RouteDetail;
import me.croco.onulmohaji.route.dto.RouteDetailAddRequest;
import me.croco.onulmohaji.route.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping("/api/routeDetail")
    public ResponseEntity<Long> addRouteDetail(@RequestBody RouteDetailAddRequest addRequest, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routeService.addRouteDetail(addRequest, request));
    }

}
