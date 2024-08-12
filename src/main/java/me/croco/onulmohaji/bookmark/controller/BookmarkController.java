package me.croco.onulmohaji.bookmark.controller;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.bookmark.service.BookmarkService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;


}
