package me.croco.onulmohaji.route.repository;

import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.route.dto.RouteDetailAddRequest;

import java.util.Optional;

public interface RouteQueryDSLRepository {
    public Optional<Long> findRouteIdByDateAndUserId(String date, Long userId);

    public Optional<Integer> findMaxOrderNoByRouteId(Long routeId);

    public Long addRouteDetail(RouteDetailAddRequest request, Member member);
}
