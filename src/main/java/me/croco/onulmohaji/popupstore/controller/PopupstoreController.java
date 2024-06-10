package me.croco.onulmohaji.popupstore.controller;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.PopplyService;
import me.croco.onulmohaji.dto.PlaceDetailFindResponse;
import me.croco.onulmohaji.exhibition.domain.Exhibition;
import me.croco.onulmohaji.exhibition.domain.ExhibitionDetail;
import me.croco.onulmohaji.localcode.domain.Localcode;
import me.croco.onulmohaji.localcode.service.LocalcodeService;
import me.croco.onulmohaji.popupstore.domain.Popupstore;
import me.croco.onulmohaji.dto.PlaceListFindResponse;
import me.croco.onulmohaji.popupstore.domain.PopupstoreDetail;
import me.croco.onulmohaji.popupstore.domain.PopupstoreImage;
import me.croco.onulmohaji.popupstore.service.PopupstoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PopupstoreController {

    private final PopplyService popplyService;
    private final PopupstoreService popupstoreService;
    private final LocalcodeService localcodeService;

    /**
     * POPPLY를 통해 새로운 팝업스토어 목록 불러와 저장
     */
    @GetMapping("/api/popup/new")
    public void getNewPopupstoreInfo() {
        popplyService.getPopupstoreInfo();
    }

    @GetMapping("/api/popup/list")
    public ResponseEntity<List<PlaceListFindResponse>> findPopupstoreListByDate(@RequestParam String date, @RequestParam Double latitude, @RequestParam Double longitude, @RequestParam(required = false) String keyword) {
        List<Popupstore> popupstoreList = popupstoreService.findPopupstoreListByDate(keyword, date, latitude, longitude);

        List<PlaceListFindResponse> popupstoreListFindResponseList = popupstoreList.stream().map(PlaceListFindResponse::new).toList();

        return ResponseEntity.ok()
                .body(popupstoreListFindResponseList);

    }

    @GetMapping("/api/popup/{id}")
    public ResponseEntity<PlaceDetailFindResponse> findPopupstoreDetail(@PathVariable String id) {
        Popupstore popupstore = popupstoreService.findPopupstoreById(id);
        PopupstoreDetail detail = popupstoreService.findPopupstoreDetailById(id);
        List<PopupstoreImage> images = popupstoreService.findPopupstoreImagesById(id);


        return ResponseEntity.ok()
                .body(new PlaceDetailFindResponse(popupstore, detail, images));
    }
}
