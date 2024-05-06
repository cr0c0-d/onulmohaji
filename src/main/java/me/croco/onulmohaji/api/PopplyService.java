package me.croco.onulmohaji.api;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.dto.PopplyPopupstoreFindResponse;
import me.croco.onulmohaji.domain.Popupstore;
import me.croco.onulmohaji.service.PopupstoreService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PopplyService {

    private static final String POPPLY_BASE_URL = "https://api.popply.co.kr";
    private static final String POPPLY_FIND_LIST_URL = "/api/store/list/";  // ?date=2024-05-06
    private static final String POPPLY_FIND_STORE_URL = "/api/store/";   // 1454 (store Id)


    private final PopupstoreService popupstoreService;


    public void getPopupstoreInfo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());

        WebClient webClient = WebClient.builder()
                .baseUrl(POPPLY_BASE_URL)
                .build();

        String response = webClient.get()
                            .uri(uriBuilder -> uriBuilder.path(POPPLY_FIND_LIST_URL)
                                    .queryParam("date", today)
                                    .build()
                            )
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();



        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(response);
            JsonNode dataNode = rootNode.path("data");

            if (!dataNode.isMissingNode()) { // 'data' 필드가 존재하는지 확인
                List<PopplyPopupstoreFindResponse> stores = mapper.convertValue(dataNode, new com.fasterxml.jackson.core.type.TypeReference<List<PopplyPopupstoreFindResponse>>() {
                });
                // stores 리스트를 사용하는 로직
                popupstoreService.savePopupstoreInfo(stores);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ObjectMapper mapper = JsonMapper.builder()
//                .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
//                .build();
//
//        Map<String, Object> popupstore = null;
//
//        try {
//            popupstore = mapper.readValue(response, Map.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        popupstoreService.savePopupstoreInfo((List<Popupstore>) popupstore.get("data"));
    }
}
