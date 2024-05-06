package me.croco.onulmohaji.controller;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.PopplyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PopupstoreController {

    private final PopplyService popplyService;

    @GetMapping("/api/popup/")
    public void getNewPopupstoreInfo() {
        popplyService.getPopupstoreInfo();
    }
}
