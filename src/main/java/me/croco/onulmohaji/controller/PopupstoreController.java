package me.croco.onulmohaji.controller;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.PopplyService;
import me.croco.onulmohaji.domain.Popupstore;
import me.croco.onulmohaji.dto.PlaceListFindResponse;
import me.croco.onulmohaji.dto.PopupstoreListFindResponse;
import me.croco.onulmohaji.service.PopupstoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PopupstoreController {

    private final PopplyService popplyService;
    private final PopupstoreService popupstoreService;

    /**
     * POPPLY를 통해 새로운 팝업스토어 목록 불러와 저장
     */
    @GetMapping("/api/popup/new")
    public void getNewPopupstoreInfo() {
        popplyService.getPopupstoreInfo();
    }

    @GetMapping("/api/popup/list")
    public ResponseEntity<List<PlaceListFindResponse>> findPopupstoreListByDate(@RequestParam String date, @RequestParam int localCode) {
        List<Popupstore> popupstoreList = popupstoreService.findPopupstoreListByDate(date);

        List<PlaceListFindResponse> popupstoreListFindResponseList = popupstoreList.stream().map(PlaceListFindResponse::new).toList();
        // 거리순으로 정렬하는 로직 추가 필요
        return ResponseEntity.ok()
                .body(popupstoreListFindResponseList);

    }
}
