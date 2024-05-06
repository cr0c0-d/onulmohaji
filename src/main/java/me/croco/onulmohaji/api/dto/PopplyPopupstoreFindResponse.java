package me.croco.onulmohaji.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import me.croco.onulmohaji.domain.PopupstoreDetail;
import me.croco.onulmohaji.domain.PopupstoreImage;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PopplyPopupstoreFindResponse {

    private Long storeId;

    private String name;

    private Long brandId;

    private int categoryId;

    private String mainBrand;

    private String brand_1;

    private String brand_2;

    private String brand_3;

    private String brand_4;

    private String title;

    private String topLevelAddress;

    private String address;

    private String detailAddress;

    private String parking;

    private boolean preRegister;

    private String preRegisterStartDate;

    private String preRegisterEndDate;

    private String preRegisterLink;

    private String hashtag;

    private String thumbnails;

    private String latitude;

    private String longitude;

    private String searchItems;

    private String startDate;

    private String endDate;

    private boolean isExpired;

    private String workingTime;

    private String status;

    private String createdAt;

    private String updatedAt;

    private PopupstoreDetail storeDetail;

    private List<PopupstoreImage> storeImage;
}
