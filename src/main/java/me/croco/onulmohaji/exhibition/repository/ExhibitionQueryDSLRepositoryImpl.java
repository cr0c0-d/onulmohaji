package me.croco.onulmohaji.exhibition.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.exhibition.domain.Exhibition;
import me.croco.onulmohaji.exhibition.domain.QExhibition;

import java.util.List;

@RequiredArgsConstructor
public class ExhibitionQueryDSLRepositoryImpl implements ExhibitionQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QExhibition qExhibition = QExhibition.exhibition;

    // 주어진 위도,경도 좌표에서부터 거리를 구함
    private NumberTemplate<Double> haversineFormula(Double latitude, Double longitude) {
        return Expressions.numberTemplate(Double.class,
                "6371 * acos(cos(radians({0})) * cos(radians({1}.gpsY)) * cos(radians({1}.gpsX) - radians({2})) + sin(radians({0})) * sin(radians({1}.gpsY)))",
                latitude, qExhibition, longitude);
    }

    @Override
    public List<Exhibition> findExhibitionListByDate(String date, Double latitude, Double longitude) {
        BooleanExpression isBefore = qExhibition.startDate.lt(date);
        BooleanExpression isAfter = qExhibition.endDate.gt(date);

        return jpaQueryFactory.selectFrom(qExhibition)
                .where(isBefore.and(isAfter))
                .orderBy(haversineFormula(latitude, longitude).asc())
                .fetch();
    }
}