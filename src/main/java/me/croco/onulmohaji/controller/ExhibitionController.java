package me.croco.onulmohaji.controller;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.ExhibitionSearchService;
import me.croco.onulmohaji.dto.ExhibitionListFindRequest;
import me.croco.onulmohaji.dto.ExhibitionListFindResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExhibitionController {

    private final ExhibitionSearchService exhibitionSearchService;
    private final ExhibitionService exhibitionService;

    @GetMapping("/api/exhibition/new")
    public void searchLocal() {
        exhibitionSearchService.getNewExhibitions();
    }

    @GetMapping("/api/exhibition")
    public ResponseEntity<ExhibitionListFindResponse> searchLocal(@RequestBody ExhibitionListFindRequest request) {
        ExhibitionListFindResponse response = exhibitionSearchService.searchExhibition(request);
        return ResponseEntity.ok()
                .body(response);
    }

}
