package me.croco.onulmohaji.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.dto.KakaoLocalListFindResponse;
import me.croco.onulmohaji.facility.domain.Facility;
import me.croco.onulmohaji.facility.service.FacilityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoLocalService {

    // api 문서
    // https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword

    private static final String KAKAO_LOCAL_BASE_URL = "https://dapi.kakao.com";
    private static final String KAKAO_LOCAL_GET_ADDRESS = "/v2/local/search/address";
    private static final String KAKAO_LOCAL_SEARCH_BY_CATEGORY = "/v2/local/search/category";

    private static final String KAKAO_LOCAL_SEARCH_DETAIL = "https://place.map.kakao.com/main/v/";  // localId

    private final FacilityService facilityService;

    @Value("${spring.security.oauth2.client.registration.kakao.client_id}")
    private String apiKey;

    public List<Double> getAddress() {
        return null;
    }

    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(KAKAO_LOCAL_BASE_URL)
                .defaultHeader("Authorization", "KakaoAK " + apiKey)
                .build();
    }

    // 카테고리 코드
//    MT1	대형마트
//    CS2	편의점
//    PS3	어린이집, 유치원
//    SC4	학교
//    AC5	학원
//    PK6	주차장
//    OL7	주유소, 충전소
//    SW8	지하철역
//    BK9	은행
//    CT1	문화시설
//    AG2	중개업소
//    PO3	공공기관
//    AT4	관광명소
//    AD5	숙박
//    FD6	음식점
//    CE7	카페
//    HP8	병원
//    PM9	약국

    // 주차장 지하철역 문화시설 관광명소 음식점 카페

    public List<Facility> getLocalListByCategory(String categoryId, Double latitude, Double longitude) {
        WebClient webClient = getWebClient();
        String response = webClient.get()
                            .uri(uriBuilder -> uriBuilder.path(KAKAO_LOCAL_SEARCH_BY_CATEGORY)
                                    .queryParam("category_group_code", categoryId)
                                    .queryParam("x", longitude)
                                    .queryParam("y", latitude)
                                    .queryParam("radius", 1000)
                                    .queryParam("sort", "accuracy")
                                    .build()
                            )
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(response);
            JsonNode dataNode = rootNode.path("documents");
            if (!dataNode.isMissingNode()) { // 'documents' 필드가 존재하는지 확인
                List<KakaoLocalListFindResponse> facilityList = mapper.convertValue(dataNode, new com.fasterxml.jackson.core.type.TypeReference<List<KakaoLocalListFindResponse>>() {
                });
                List<Facility> facilities = facilityList.stream().map(Facility::new).toList();

                facilities.forEach(facility -> {
                    Map<String, String> facilityDetail = getFacilityDetail(facility.getId());
                    facility.setThumbnail(facilityDetail.get("thumbnail"));
                    facility.setScoresum(Integer.parseInt(facilityDetail.get("scoresum")));
                    facility.setScorecnt(Integer.parseInt(facilityDetail.get("scorecnt")));
                });

                facilities = facilities.stream()
                        .sorted(Comparator.comparingInt(Facility::getScorecnt))
                        .toList();
                return facilityService.saveAll(facilities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Facility의 상세정보 조회
    public Map<String, String> getFacilityDetail(Long facilityId) {
        Map<String, String> facilityDetail = new HashMap<>();

        String response = WebClient.create(KAKAO_LOCAL_SEARCH_DETAIL)
                .get()
                .uri(String.valueOf(facilityId))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(response);
            JsonNode dataNode = rootNode.path("basicInfo");
            if (!dataNode.isMissingNode()) { // 'basicInfo' 필드가 존재하는지 확인
                facilityDetail.put("thumbnail", mapper.convertValue(dataNode.path("mainphotourl"), String.class));
                int scoresum = mapper.convertValue(dataNode.path("feedback").path("scoresum"), Integer.class);
                int scorecnt = mapper.convertValue(dataNode.path("feedback").path("scorecnt"), Integer.class);
                facilityDetail.put("scoresum", String.valueOf(scoresum));
                facilityDetail.put("scorecnt", String.valueOf(scorecnt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facilityDetail;
    }

}
