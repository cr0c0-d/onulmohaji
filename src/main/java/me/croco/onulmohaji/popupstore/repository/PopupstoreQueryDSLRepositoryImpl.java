package me.croco.onulmohaji.popupstore.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
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

    @Override
    public List<Popupstore> findPopupstoreListByDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BooleanExpression isBefore = qPopupstore.startDate.lt(formatter.format(date));
        BooleanExpression isAfter = qPopupstore.endDate.gt(formatter.format(date));

        return jpaQueryFactory.selectFrom(qPopupstore)
                .where(isBefore.and(isAfter))
                .fetch();
    }
}
