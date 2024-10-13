package me.croco.onulmohaji.bookmark.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.bookmark.domain.Bookmark;
import me.croco.onulmohaji.bookmark.dto.BookmarkAddRequest;
import me.croco.onulmohaji.bookmark.dto.BookmarkListFindResponse;
import me.croco.onulmohaji.bookmark.service.BookmarkService;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final MemberService memberService;

    @PostMapping("/api/bookmark")
    public ResponseEntity<Boolean> toggleBookmark(@RequestBody BookmarkAddRequest addRequest, HttpServletRequest request) {
        Member loginMember = memberService.getLoginMember(request);
        Boolean bookmarkYn = bookmarkService.toggleBookmark(addRequest, loginMember);

        return ResponseEntity.ok()
                .body(bookmarkYn);
    }

    @GetMapping("/api/bookmark")
    public ResponseEntity<List<BookmarkListFindResponse>> getBookmarkList(HttpServletRequest request) {
        Member loginMember = memberService.getLoginMember(request);
        List<Bookmark> bookmarkList = bookmarkService.getBookmarkList(loginMember);

        return ResponseEntity.ok()
                .body(bookmarkList.stream().map(BookmarkListFindResponse::new).toList());

    }

}
