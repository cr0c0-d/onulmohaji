package me.croco.onulmohaji.route.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.route.domain.QRoute;
import me.croco.onulmohaji.route.domain.QRouteDetail;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.dto.RouteDetailAddRequest;
import me.croco.onulmohaji.route.dto.RouteDetailUpdateRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RouteQueryDSLRepositoryImpl implements RouteQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QRoute qRoute = QRoute.route;
    private final QRouteDetail qRouteDetail = QRouteDetail.routeDetail;


    @Override
    public Optional<Route> findRouteByDateAndUserId(String date, Long userId) throws IllegalArgumentException {

        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(qRoute)
                        .where(qRoute.routeDate.eq(date)
                            .and(qRoute.userId.eq(userId)
                        )
                )
                .fetchOne()
        );
    }

    @Override
    public Optional<Integer> findMaxOrderNoByRouteId(Long routeId) {
        return Optional.ofNullable(
                jpaQueryFactory.select(qRouteDetail.orderNo.max())
                        .from(qRouteDetail)
                        .where(qRouteDetail.routeId.eq(routeId))
                .fetchOne()
        );
    }

    @Override
    @Transactional
    public void updateRouteDetailOrder(List<RouteDetailUpdateRequest> routeDetailUpdateRequests) {
        routeDetailUpdateRequests.forEach(routeDetailUpdateRequest -> {
            jpaQueryFactory.update(qRouteDetail)
                    .set(qRouteDetail.orderNo, routeDetailUpdateRequest.getOrderNo())
                    .where(qRouteDetail.id.eq(routeDetailUpdateRequest.getId()))
                    .execute();
        });
    }


}
