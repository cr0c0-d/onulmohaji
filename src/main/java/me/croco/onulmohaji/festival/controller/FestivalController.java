package me.croco.onulmohaji.festival.controller;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.KoreanFestivalService;
import me.croco.onulmohaji.festival.domain.Festival;
import me.croco.onulmohaji.festival.service.FestivalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FestivalController {

    private final KoreanFestivalService koreanFestivalService;
    private final FestivalService festivalService;

    @GetMapping("/festival/new")
    public ResponseEntity<List<Festival>> getNewFestivalList() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(festivalService.saveNewFestivalList());
    }
}
