package me.croco.onulmohaji.exhibition.controller;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.PublicExhibitionService;
import me.croco.onulmohaji.exhibition.domain.Exhibition;
import me.croco.onulmohaji.dto.PlaceListFindResponse;
import me.croco.onulmohaji.exhibition.service.ExhibitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExhibitionController {

    private final PublicExhibitionService publicExhibitionService;
    private final ExhibitionService exhibitionService;

    @GetMapping("/api/exhibition/new")
    public void searchLocal() {
        publicExhibitionService.getNewExhibitions();
    }

    @GetMapping("/api/exhibition/list")
    public ResponseEntity<List<PlaceListFindResponse>> findExhibitionListByDate(@RequestParam String date, @RequestParam int localCode) {
        List<Exhibition> exhibitionList = exhibitionService.findExhibitionListByDate(date);

        List<PlaceListFindResponse> popupstoreListFindResponseList = exhibitionList.stream().map(PlaceListFindResponse::new).toList();
        // 거리순으로 정렬하는 로직 추가 필요
        return ResponseEntity.ok()
                .body(popupstoreListFindResponseList);

    }

}
