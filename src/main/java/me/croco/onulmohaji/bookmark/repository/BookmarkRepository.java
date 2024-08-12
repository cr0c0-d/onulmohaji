package me.croco.onulmohaji.bookmark.repository;

import me.croco.onulmohaji.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
