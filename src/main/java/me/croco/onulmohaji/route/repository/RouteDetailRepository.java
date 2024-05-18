package me.croco.onulmohaji.route.repository;

import me.croco.onulmohaji.route.domain.RouteDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteDetailRepository extends JpaRepository<RouteDetail, Long>, RouteQueryDSLRepository {
}
