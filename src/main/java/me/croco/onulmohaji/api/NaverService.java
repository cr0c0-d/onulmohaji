package me.croco.onulmohaji.api;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.config.WebClientConfig;
import me.croco.onulmohaji.dto.NaverLocalFindRequest;
import me.croco.onulmohaji.dto.NaverLocalFindResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NaverService {

    @Value("${spring.security.oauth2.client.registration.naver.client_id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    private static final String NAVER_API_BASE_URL = "https://openapi.naver.com";

    private static final String SEARCH_LOCAL_URI = "/v1/search/local.json";

    public NaverLocalFindResponse searchLocal(NaverLocalFindRequest request) {
        WebClient webClient = getNaverApiWebClient();
//        String uri = UriComponentsBuilder.fromUriString(SEARCH_LOCAL_URI)
//                .queryParam("query", request.getQuery())
//                .queryParam("display", 5)
//                .queryParam("start", 1)
//                .queryParam("sort", "comment")
//                .build()
//                .encode()
//                .toUriString();
        String uri = SEARCH_LOCAL_URI + "?query=" + request.getQuery();

        String responseBody = webClient.get()
                                .uri(uri)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();

        ObjectMapper mapper = JsonMapper.builder()
                                .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
                                .build();
        NaverLocalFindResponse response = null;

        try {
            response = mapper.readValue(responseBody, NaverLocalFindResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getNaverLoginRequestUrl(String state) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", naverClientId);
        params.put("response_type", "code");
        params.put("redirect_uri", "http://25.10.86.27:3000/login/auth/naver/callback");
        params.put("state", state);

        StringBuilder builder = new StringBuilder();
        builder.append("https://nid.naver.com/oauth2.0/authorize");
        builder.append("?");
        params.keySet().forEach(key -> {
            builder.append(key);
            builder.append("=");
            builder.append(params.get(key));
            builder.append("&");
        });
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();

    private WebClient getNaverApiWebClient() {
        return WebClient.builder()
                .baseUrl(NAVER_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Naver-Client-Id", naverClientId)
                .defaultHeader("X-Naver-Client-Secret", naverClientSecret)
                .build();
    }
}
