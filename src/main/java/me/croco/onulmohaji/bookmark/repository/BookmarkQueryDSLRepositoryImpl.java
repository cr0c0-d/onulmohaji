package me.croco.onulmohaji.bookmark.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.bookmark.domain.QBookmark;

import java.util.List;

@RequiredArgsConstructor
public class BookmarkQueryDSLRepositoryImpl implements BookmarkQueryDSLRepository{

    private final JPAQueryFactory jpaQueryFactory;

    private final QBookmark qBookmark = QBookmark.bookmark;

    @Override
    public List<String> findPopupstoreBookmark(Long userId) {
        return jpaQueryFactory.select(qBookmark.placeId)
                .from(qBookmark)
                .where(qBookmark.placeType.eq("popup")
                        .and(qBookmark.userId.eq(userId))
                )
                .fetch();
    }
}
