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
    private String id;
    private int categoryId;
    private String category;
    private String title;
    private String address_short;
    private String address;
    private boolean preRegister;
    private String preRegisterStartDate;
    private String preRegisterEndDate;
    private String preRegisterLink;
    private String thumbnails;
    private Double latitude;
    private Double longitude;
    private String startDate;
    private String endDate;

    public PlaceListFindResponse(Popupstore popupstore) {
        this.id = String.valueOf(popupstore.getStoreId());
        this.title = HtmlUtils.htmlUnescape(popupstore.getTitle());
        this.categoryId = popupstore.getCategoryId();
        this.address_short = popupstore.getTopLevelAddress();
        this.address = popupstore.getAddress();
        this.preRegister = popupstore.isPreRegister();
        this.preRegisterStartDate = popupstore.getPreRegisterStartDate();
        this.preRegisterEndDate = popupstore.getPreRegisterEndDate();
        this.preRegisterLink = popupstore.getPreRegisterLink();
        this.thumbnails = popupstore.getThumbnails();
        this.latitude = popupstore.getLatitude();
        this.longitude = popupstore.getLongitude();
        this.startDate = popupstore.getStartDate();
        this.endDate = popupstore.getEndDate();
    }

    public PlaceListFindResponse(Exhibition exhibition) {
        this.id = String.valueOf(exhibition.getSeq());
        this.title = HtmlUtils.htmlUnescape(exhibition.getTitle());
        this.startDate = LocalDate.parse(exhibition.getStartDate()).format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.endDate = LocalDate.parse(exhibition.getEndDate()).format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.address_short = exhibition.getArea() + " " + exhibition.getPlace();
        this.category = exhibition.getRealmName();
        this.thumbnails = exhibition.getThumbnail();
        this.latitude = exhibition.getGpsX();
        this.longitude = exhibition.getGpsY();
    }

    public PlaceListFindResponse(Festival festival) {
        this.id = festival.getId();
        this.title = festival.getTitle();
        this.address_short = festival.getAreaNm();
        this.address = festival.getAddress();
        this.thumbnails = festival.getThumbnail();
        this.latitude = festival.getLatitude();
        this.longitude = festival.getLongitude();
        this.startDate = festival.getStartDate();
        this.endDate = festival.getEndDate();
    }
}
