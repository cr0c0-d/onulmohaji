package me.croco.onulmohaji.facility.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.KakaoLocalService;
import me.croco.onulmohaji.facility.domain.Facility;
import me.croco.onulmohaji.facility.service.FacilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;
    private final KakaoLocalService kakaoLocalService;


    @GetMapping("/api/facility")
    public ResponseEntity<List<Facility>> findFacility(@RequestParam String categoryId, @RequestParam Double latitude, @RequestParam Double longitude) {
        List<Facility> facilityList = kakaoLocalService.getLocalListByCategory(categoryId, latitude, longitude);
        return ResponseEntity.ok()
                .body(facilityList);
    }

}
