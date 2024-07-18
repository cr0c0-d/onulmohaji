package me.croco.onulmohaji.facility.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.KakaoLocalService;
import me.croco.onulmohaji.facility.domain.Facility;
import me.croco.onulmohaji.facility.dto.FacilityFindResponse;
import me.croco.onulmohaji.facility.dto.FacilityListFindResponse;
import me.croco.onulmohaji.facility.repository.FacilityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

//    public List<Facility> findLocalFacilityList(String keyword, Double latitude, Double longitude) {
//
//
//        List<Facility> facilityList = null;
//        if(keyword == null) {
//            facilityList = facilityRepository.findDefaultFacilityList(latitude, longitude);
//
//            // DB에 저장된 facility가 100개 미만인 경우 카카오맵에서 가져와서 저장하기
//            if(facilityList.size() < 100) {
//                CompletableFuture.runAsync(() -> {
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("이색데이트", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("실내놀거리", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("테마파크", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("테마카페", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("문화", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("산책", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("레저", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("명소", null, latitude, longitude));
//                });
//            }
//        } else {
//            //facilityList = facilityRepository.findFacilityListByKeyword(keyword, latitude, longitude);
//            facilityList = facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory(keyword, null, latitude, longitude));
//        }
//        return facilityList;
//    }
    public List<FacilityListFindResponse> findLocalFacilityList(String keyword, Double latitude, Double longitude) {
        List<FacilityListFindResponse> responses = new ArrayList<>();

        /**
         * 식당, 테마카페, 관광명소, 문화예술, 실내놀거리
         */
        if(keyword == null) {
            List<FacilityFindResponse> food = facilityRepository.findFacilityListByCategory("음식점", latitude, longitude).stream().map(FacilityFindResponse::new).toList();
            List<FacilityFindResponse> cafe = facilityRepository.findFacilityListByCategory("테마카페", latitude, longitude).stream().map(FacilityFindResponse::new).toList();
            List<FacilityFindResponse> landmark = facilityRepository.findFacilityListByCategory("관광,명소", latitude, longitude).stream().map(FacilityFindResponse::new).toList();
            List<FacilityFindResponse> art = facilityRepository.findFacilityListByCategory("문화,예술", latitude, longitude).stream().map(FacilityFindResponse::new).toList();
            List<FacilityFindResponse> playground = facilityRepository.findFacilityListByCategory("실내놀거리", latitude, longitude).stream().map(FacilityFindResponse::new).toList();

            responses.add(new FacilityListFindResponse("음식점", food));
            responses.add(new FacilityListFindResponse("테마카페", cafe));
            responses.add(new FacilityListFindResponse("관광명소", landmark));
            responses.add(new FacilityListFindResponse("문화예술", art));
            responses.add(new FacilityListFindResponse("실내놀거리", playground));


//            // DB에 저장된 facility가 100개 미만인 경우 카카오맵에서 가져와서 저장하기
//            if(facilityList.size() < 100) {
//                CompletableFuture.runAsync(() -> {
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("이색데이트", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("실내놀거리", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("테마파크", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("테마카페", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("문화", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("산책", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("레저", null, latitude, longitude));
//                    facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory("명소", null, latitude, longitude));
//                });
//            }
        } else {
            //facilityList = facilityRepository.findFacilityListByKeyword(keyword, latitude, longitude);
            //facilityList = facilityRepository.saveAll(kakaoLocalService.getLocalListByCategory(keyword, null, latitude, longitude));
        }
        return responses;
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
