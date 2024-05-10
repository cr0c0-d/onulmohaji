package me.croco.onulmohaji.popupstore.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.popupstore.domain.Popupstore;
import me.croco.onulmohaji.popupstore.domain.QPopupstore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class PopupstoreQueryDSLRepositoryImpl implements PopupstoreQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QPopupstore qPopupstore = QPopupstore.popupstore;

    // 주어진 위도,경도 좌표에서부터 거리를 구함
    private NumberTemplate<Double> haversineFormula(Double latitude, Double longitude) {
        return Expressions.numberTemplate(Double.class,
                "6371 * acos(cos(radians({0})) * cos(radians({1}.latitude)) * cos(radians({1}.longitude) - radians({2})) + sin(radians({0})) * sin(radians({1}.latitude)))",
                latitude, qPopupstore, longitude);
    }

    @Override
    public List<Popupstore> findPopupstoreListByDate(LocalDate date, Double latitude, Double longitude) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BooleanExpression isBefore = qPopupstore.startDate.lt(formatter.format(date));
        BooleanExpression isAfter = qPopupstore.endDate.gt(formatter.format(date));

        return jpaQueryFactory.selectFrom(qPopupstore)
                .where(isBefore.and(isAfter))
                .orderBy(haversineFormula(latitude, longitude).asc())
                .fetch();
    }
}
