package me.croco.onulmohaji.bookmark.service;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.bookmark.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
}
