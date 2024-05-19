package me.croco.onulmohaji.route.repository;

import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.dto.RouteDetailAddRequest;

import java.util.Optional;

public interface RouteQueryDSLRepository {
    public Optional<Route> findRouteByDateAndUserId(String date, Long userId);

    public Optional<Integer> findMaxOrderNoByRouteId(Long routeId);

}
