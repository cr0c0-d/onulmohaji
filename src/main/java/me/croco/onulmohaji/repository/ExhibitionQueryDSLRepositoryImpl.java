package me.croco.onulmohaji.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.domain.Exhibition;
import me.croco.onulmohaji.domain.QExhibition;

import java.util.List;

@RequiredArgsConstructor
public class ExhibitionQueryDSLRepositoryImpl implements ExhibitionQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QExhibition qExhibition = QExhibition.exhibition;

    @Override
    public List<Exhibition> findExhibitionListByDate(String date) {
        BooleanExpression isBefore = qExhibition.startDate.lt(date);
        BooleanExpression isAfter = qExhibition.endDate.gt(date);

        return jpaQueryFactory.selectFrom(qExhibition)
                .where(isBefore.and(isAfter))
                .fetch();
    }
}
