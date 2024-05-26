package me.croco.onulmohaji.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.croco.onulmohaji.api.dto.KoreanFestivalListFindResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KoreanFestivalService {
    private static final String KOREAN_VISIT_KOREA_BASE_URL = "https://korean.visitkorea.or.kr";
    private static final String KOREAN_VISIT_FIND_FESTIVAL_LIST = "/kfes/list/selectWntyFstvlList.do";

    public List<KoreanFestivalListFindResponse> getFestivalList() {
        List<KoreanFestivalListFindResponse> festivalList = new ArrayList<>();

        Map<String, String> params = new HashMap<>();
        params.put("searchType", "A");
        params.put("searchDate", "05");
        params.put("searchArea", "");
        params.put("searchCate", "");
        params.put("locationx", "undefined");
        params.put("locationy", "undefined");
        params.put("filterExcluded", "true");

        int startIdx = 0;

        while (true) {
            params.put("startIdx", String.valueOf(startIdx));

            String response = getWebClient().get()
                    .uri(KOREAN_VISIT_FIND_FESTIVAL_LIST)
                    .attribute("body", params)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode rootNode = mapper.readTree(response);
                JsonNode dataNode = rootNode.path("resultList");
                if (!dataNode.isMissingNode()) { // 'resultList' 필드가 존재하는지 확인
                    List<KoreanFestivalListFindResponse> newFestivals = mapper.convertValue(dataNode, new com.fasterxml.jackson.core.type.TypeReference<List<KoreanFestivalListFindResponse>>() {
                    });
                    if(newFestivals.isEmpty()) {
                        break;
                    } else {
                        festivalList.addAll(newFestivals);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //startIdx += 12;
            break;
        }

        return festivalList;
    }

    private WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(KOREAN_VISIT_KOREA_BASE_URL)
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
    }
}
