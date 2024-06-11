package me.croco.onulmohaji.dto;

import lombok.Getter;
import lombok.Setter;
import me.croco.onulmohaji.exhibition.domain.Exhibition;
import me.croco.onulmohaji.festival.domain.Festival;
import me.croco.onulmohaji.popupstore.domain.Popupstore;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class PlaceListFindResponse {
    private String placeId;
    private int categoryId;
    private String category;
    private String placeName;
    private String placeType;
    private String placeTypeName;
    private String address_short;
    private String address;
    private boolean preRegister;
    private String preRegisterStartDate;
    private String preRegisterEndDate;
    private String preRegisterLink;
    private String thumbnail;
    private Double latitude;
    private Double longitude;
    private String startDate;
    private String endDate;
    private String placeUrl;

    public PlaceListFindResponse(Popupstore popupstore) {
        this.placeId = String.valueOf(popupstore.getStoreId());
        this.placeName = HtmlUtils.htmlUnescape(popupstore.getTitle());
        this.categoryId = popupstore.getCategoryId();
        this.address_short = popupstore.getTopLevelAddress();
        this.address = popupstore.getAddress();
        this.preRegister = popupstore.isPreRegister();
        this.preRegisterStartDate = popupstore.getPreRegisterStartDate();
        this.preRegisterEndDate = popupstore.getPreRegisterEndDate();
        this.preRegisterLink = popupstore.getPreRegisterLink();
        this.thumbnail = popupstore.getThumbnails();
        this.latitude = popupstore.getLatitude();
        this.longitude = popupstore.getLongitude();
        this.startDate = popupstore.getStartDate();
        this.endDate = popupstore.getEndDate();
        this.placeType = "popup";
        this.placeTypeName = "팝업스토어";
        this.placeUrl = "/popup/" + popupstore.getStoreId();
    }

    public PlaceListFindResponse(Exhibition exhibition) {
        this.placeId = String.valueOf(exhibition.getSeq());
        this.placeName = HtmlUtils.htmlUnescape(exhibition.getTitle());
        this.startDate = LocalDate.parse(exhibition.getStartDate()).format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.endDate = LocalDate.parse(exhibition.getEndDate()).format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.address_short = exhibition.getArea() + " " + exhibition.getPlace();
        this.category = exhibition.getRealmName();
        this.thumbnail = exhibition.getThumbnail();
        this.latitude = exhibition.getGpsX();
        this.longitude = exhibition.getGpsY();
        this.placeType = "exhibition";
        this.placeTypeName = "전시회/공연";
        this.placeUrl = "/exhibition/" + exhibition.getSeq();
    }

    public PlaceListFindResponse(Festival festival) {
        this.placeId = festival.getId();
        this.placeName = festival.getTitle();
        this.address_short = festival.getAreaNm();
        this.address = festival.getAddress();
        this.thumbnail = festival.getThumbnail();
        this.latitude = festival.getLatitude();
        this.longitude = festival.getLongitude();
        this.startDate = festival.getStartDate();
        this.endDate = festival.getEndDate();
        this.placeType = "festival";
        this.placeTypeName = "축제";
        this.placeUrl = "/festival/" + festival.getId();
    }
}
