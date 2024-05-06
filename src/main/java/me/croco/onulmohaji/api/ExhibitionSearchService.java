package me.croco.onulmohaji.api;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.xml.bind.JAXB;
import me.croco.onulmohaji.dto.ExhibitionListFindRequest;
import me.croco.onulmohaji.dto.ExhibitionListFindResponse;
import me.croco.onulmohaji.dto.NaverLocalFindResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class ExhibitionSearchService {

    private static final String BASE_URL = "http://www.culture.go.kr";

    private static final String SEARCH_EXHIBITION_URL = "/openapi/rest/publicperformancedisplays/period";

    @Value("${apiKey.exhibitionSearch1}")
    private String apiKey;

    private WebClient getWebClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        return WebClient.builder()
                .baseUrl(BASE_URL)
                .uriBuilderFactory(factory)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public ExhibitionListFindResponse searchExhibition(ExhibitionListFindRequest request) {
        WebClient webClient = getWebClient();
        String responseBody = webClient.get()
                                .uri(uriBuilder -> uriBuilder.path(SEARCH_EXHIBITION_URL)
                                        .queryParam("from", URLEncoder.encode("20240501", StandardCharsets.UTF_8))
                                        .queryParam("to", URLEncoder.encode("20240530", StandardCharsets.UTF_8))
                                        .queryParam("cPage", URLEncoder.encode("1", StandardCharsets.UTF_8))
                                        .queryParam("rows", URLEncoder.encode("10", StandardCharsets.UTF_8))
                                        .queryParam("place", URLEncoder.encode("", StandardCharsets.UTF_8))
                                        .queryParam("gpsxfrom", URLEncoder.encode("129.1013129", StandardCharsets.UTF_8))
                                        .queryParam("gpsyfrom", URLEncoder.encode("35.1416412", StandardCharsets.UTF_8))
                                        .queryParam("gpsxto", URLEncoder.encode("129.1013129", StandardCharsets.UTF_8))
                                        .queryParam("gpsyto", URLEncoder.encode("35.1416412", StandardCharsets.UTF_8))
                                        .queryParam("keyword", URLEncoder.encode(request.getKeyword(), StandardCharsets.UTF_8))
                                        .queryParam("sortStdr", URLEncoder.encode("1", StandardCharsets.UTF_8))
                                        .queryParam("serviceKey", apiKey)
                                        .build()
                                )
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();

        ExhibitionListFindResponse response = JAXB.unmarshal(responseBody, ExhibitionListFindResponse.class);

//        ObjectMapper mapper = builder()
//                .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
//                .build();
//        ExhibitionListFindResponse response = null;
//
//        try {
//            response = mapper.readValue(responseBody, ExhibitionListFindResponse.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return response;
    }
}
