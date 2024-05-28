package me.croco.onulmohaji.facility.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.KakaoLocalService;
import me.croco.onulmohaji.facility.domain.Facility;
import me.croco.onulmohaji.facility.repository.FacilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final KakaoLocalService kakaoLocalService;

    public List<Facility> getNewFacilities(String keyword, String categoryId, Double latitude, Double longitude) {
        List<Facility> facilityList = kakaoLocalService.getLocalListByCategory(keyword, categoryId, latitude, longitude);
        return facilityRepository.saveAll(facilityList);
    }

    public List<Facility> findLocalFacilityList(String keyword, Double latitude, Double longitude) {
        CompletableFuture.runAsync(() -> {
            facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("이색데이트", null, latitude, longitude));
            facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("실내놀거리", null, latitude, longitude));
            facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("테마파크", null, latitude, longitude));
            facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("테마카페", null, latitude, longitude));
        });

        List<Facility> facilityList = null;
        if(keyword == null) {
            facilityList = facilityRepository.findDefaultFacilityList(latitude, longitude);
        } else {
            facilityList = facilityRepository.findFacilityListByKeyword(keyword, latitude, longitude);
        }
        return facilityList;
    }

    public List<Facility> findFoodListByPlace(Double latitude, Double longitude) {
        CompletableFuture.runAsync(() -> facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory(null, "FD6", latitude, longitude)));

        List<Tuple> list = facilityRepository.findFoodListByPlace(latitude, longitude);
        return list.stream().map(tuple -> {
            Facility facility = tuple.get(0, Facility.class);
            Double distance = tuple.get(1, Double.class);

            facility.setDistance((int) Math.floor(distance*1000));
            return facility;
        }).toList();
    }
}
