package me.croco.onulmohaji.bookmark.repository;

import me.croco.onulmohaji.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserIdAndPlaceTypeAndPlaceId(Long userId, String placeType, String placeId);
}
