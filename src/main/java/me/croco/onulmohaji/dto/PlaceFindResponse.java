package me.croco.onulmohaji.dto;

import lombok.Getter;
import lombok.Setter;
import me.croco.onulmohaji.exhibition.domain.Exhibition;
import me.croco.onulmohaji.popupstore.domain.Popupstore;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class PlaceFindResponse {
    private Long id;
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
    private String latitude;
    private String longitude;
    private String startDate;
    private String endDate;

    public PlaceFindResponse(Popupstore popupstore) {
        this.id = popupstore.getStoreId();
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

    public PlaceFindResponse(Exhibition exhibition) {
        this.id = exhibition.getSeq();
        this.title = HtmlUtils.htmlUnescape(exhibition.getTitle());
        this.startDate = LocalDate.parse(exhibition.getStartDate()).format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.endDate = LocalDate.parse(exhibition.getEndDate()).format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.address_short = exhibition.getArea() + " " + exhibition.getPlace();
        this.category = exhibition.getRealmName();
        this.thumbnails = exhibition.getThumbnail();
        this.latitude = exhibition.getGpsX();
        this.longitude = exhibition.getGpsY();
    }
}
