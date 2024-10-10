package me.croco.onulmohaji.bookmark.repository;

import java.util.List;

public interface BookmarkQueryDSLRepository {
    List<String> findPopupstoreBookmark(Long userId);
}
