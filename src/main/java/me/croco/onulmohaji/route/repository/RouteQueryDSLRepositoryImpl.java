package me.croco.onulmohaji.route.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.route.domain.QRoute;
import me.croco.onulmohaji.route.domain.QRouteDetail;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.dto.RouteDetailAddRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
public class RouteQueryDSLRepositoryImpl implements RouteQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QRoute qRoute = QRoute.route;
    private final QRouteDetail qRouteDetail = QRouteDetail.routeDetail;


    @Override
    public Optional<Long> findRouteIdByDateAndUserId(String date, Long userId) throws IllegalArgumentException {

        return Optional.ofNullable(
                jpaQueryFactory.select(qRoute.id)
                        .from(qRoute)
                        .where(qRoute.routeDate.eq(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
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
    public Long addRouteDetail(RouteDetailAddRequest request, Member member) {
        Route route = jpaQueryFactory.selectFrom(qRoute)
                .where(qRoute.routeDate.eq(LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .and(
                                qRoute.userId.eq(member.getId())
                        )

                ).fetchOne();
        Long routeId;
        Integer maxOrder = 1;

        if (route == null) {
            routeId = jpaQueryFactory.insert(qRoute)
                    .columns(qRoute.routeDate, qRoute.userId, qRoute.likeCnt, qRoute.shareType, qRoute.valid)
                    .values(LocalDate.parse(request.getDate()), member.getId(), 0, 0, 1)
                    .execute();
        } else {
            routeId = route.getId();

            maxOrder = jpaQueryFactory.select(qRouteDetail.orderNo.max())
                    .where(qRouteDetail.routeId.eq(routeId))
                    .fetchOne();

            if(maxOrder == null) {
                maxOrder = 1;
            }
        }

        return jpaQueryFactory.insert(qRouteDetail)
                .columns(qRouteDetail.routeId, qRouteDetail.orderNo, qRouteDetail.placeId, qRouteDetail.placeType)
                .values(routeId, maxOrder, request.getPlaceId(), request.getPlaceType())
                .execute();
    }
}
