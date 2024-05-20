package me.croco.onulmohaji.route.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import me.croco.onulmohaji.exhibition.domain.Exhibition;
import me.croco.onulmohaji.facility.domain.Facility;
import me.croco.onulmohaji.popupstore.domain.Popupstore;
import me.croco.onulmohaji.route.domain.RouteDetail;

@Setter
@Getter
public class RouteDetailFindResponse {

    private Long id;

    private Long routeId;

    private int orderNo;

    private Long placeId;

    private String placeType;

    private String placeName;

    private String placeTypeName;

    private String thumbnail;

    private Long wpointx;

    private Long wpointy;

    public RouteDetailFindResponse(RouteDetail routeDetail, Exhibition exhibition) {
        this.id = routeDetail.getId();
        this.routeId = routeDetail.getRouteId();
        this.orderNo = routeDetail.getOrderNo();
        this.placeId = routeDetail.getPlaceId();
        this.placeType = routeDetail.getPlaceType();

        this.thumbnail = exhibition.getThumbnail();
        this.placeName = exhibition.getTitle();
        this.placeTypeName = "전시회 / 공연";
        this.wpointx = exhibition.getWpointx();
        this.wpointy = exhibition.getWpointy();
    }

    public RouteDetailFindResponse(RouteDetail routeDetail, Popupstore popupstore) {
        this.id = routeDetail.getId();
        this.routeId = routeDetail.getRouteId();
        this.orderNo = routeDetail.getOrderNo();
        this.placeId = routeDetail.getPlaceId();
        this.placeType = routeDetail.getPlaceType();

        this.thumbnail = popupstore.getThumbnails();
        this.placeName = popupstore.getTitle();
        this.placeTypeName = "팝업스토어";
        this.wpointx = popupstore.getWpointx();
        this.wpointy = popupstore.getWpointy();
    }

    public RouteDetailFindResponse(RouteDetail routeDetail, Facility facility) {
        this.id = routeDetail.getId();
        this.routeId = routeDetail.getRouteId();
        this.orderNo = routeDetail.getOrderNo();
        this.placeId = routeDetail.getPlaceId();
        this.placeType = routeDetail.getPlaceType();

        this.thumbnail = facility.getThumbnail();
        this.placeName = facility.getPlaceName();

        StringBuilder builder = new StringBuilder();
        builder.append(facility.getCategoryName());
        builder.reverse();
        builder.delete(builder.indexOf(" > "), builder.length());
        builder.reverse();
        this.placeTypeName = builder.toString();
        this.wpointx = facility.getWpointx();
        this.wpointy = facility.getWpointy();
    }
}
