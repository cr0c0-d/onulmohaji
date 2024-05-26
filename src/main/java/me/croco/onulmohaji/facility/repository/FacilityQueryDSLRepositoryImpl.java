package me.croco.onulmohaji.facility.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.facility.domain.Facility;
import me.croco.onulmohaji.facility.domain.QFacility;

import java.util.List;

@RequiredArgsConstructor
public class FacilityQueryDSLRepositoryImpl implements FacilityQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QFacility qFacility = QFacility.facility;

    // 주어진 위도,경도 좌표에서부터 거리를 구함
    private NumberTemplate<Double> haversineFormula(Double latitude, Double longitude) {
        return Expressions.numberTemplate(Double.class,
                "6371 * acos(cos(radians({0})) * cos(radians({1}.latitude)) * cos(radians({1}.longitude) - radians({2})) + sin(radians({0})) * sin(radians({1}.latitude)))",
                latitude, qFacility, longitude);
    }

    @Override
    public List<Tuple> findFoodListByPlace(Double latitude, Double longitude) {
        return jpaQueryFactory.select(qFacility, haversineFormula(latitude, longitude))
                .from(qFacility)
                .where(qFacility.categoryGroupCode.eq("FD6")
                        .and(haversineFormula(latitude, longitude).lt(3))
                )
                .orderBy(qFacility.scorecnt.desc())
                .limit(20)
                .fetch();
    }
}
