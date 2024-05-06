package me.croco.onulmohaji.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import me.croco.onulmohaji.api.dto.PopplyPopupstoreFindResponse;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Popupstore {

    @Id
    @Column(name = "store_id")
    private Long storeId;

    @Column
    private String name;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "main_brand")
    private String mainBrand;

    @Column
    private String brand_1;

    @Column
    private String brand_2;

    @Column
    private String brand_3;

    @Column
    private String brand_4;

    @Column
    private String title;

    @Column(name = "top_level_address")
    private String topLevelAddress;

    @Column
    private String address;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column
    private String parking;

    @Column(name = "pre_register")
    private boolean preRegister;

    @Column(name = "pre_register_start_date")
    private String preRegisterStartDate;

    @Column(name = "pre_register_end_date")
    private String preRegisterEndDate;

    @Column(name = "pre_register_link")
    private String preRegisterLink;

    @Column
    private String hashtag;

    @Column
    private String thumbnails;

    @Column
    private String latitude;

    @Column
    private String longitude;

    @Column(name = "search_items")
    private String searchItems;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(name = "working_time")
    private String workingTime;

    @Column
    private String status;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Builder
    public Popupstore(PopplyPopupstoreFindResponse response) {
        this.storeId = response.getStoreId();
        this.name = response.getName();
        this.brandId = response.getBrandId();
        this.categoryId = response.getCategoryId();
        this.mainBrand = response.getMainBrand();
        this.brand_1 = response.getBrand_1();
        this.brand_2 = response.getBrand_2();
        this.brand_3 = response.getBrand_3();
        this.brand_4 = response.getBrand_4();
        this.title = response.getTitle();
        this.topLevelAddress = response.getTopLevelAddress();
        this.address = response.getAddress();
        this.detailAddress = response.getDetailAddress();
        this.parking = response.getParking();
        this.preRegister = response.isPreRegister();
        this.preRegisterStartDate = response.getPreRegisterStartDate();
        this.preRegisterEndDate = response.getPreRegisterEndDate();
        this.preRegisterLink = response.getPreRegisterLink();
        this.hashtag = response.getHashtag();
        this.thumbnails = response.getThumbnails();
        this.latitude = response.getLatitude();
        this.longitude = response.getLongitude();
        this.searchItems = response.getSearchItems();
        this.startDate = response.getStartDate();
        this.endDate = response.getEndDate();
        this.isExpired = response.isExpired();
        this.workingTime = response.getWorkingTime();
        this.status = response.getStatus();
        this.createdAt = response.getCreatedAt();
        this.updatedAt = response.getUpdatedAt();
    }

}

