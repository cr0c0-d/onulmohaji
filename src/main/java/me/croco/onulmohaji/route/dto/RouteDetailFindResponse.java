package me.croco.onulmohaji.route.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
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

    private Double latitude;

    private Double longitude;

    private String address;

    public RouteDetailFindResponse(RouteDetail routeDetail) {
        this.id = routeDetail.getId();
        this.routeId = routeDetail.getRouteId();
        this.orderNo = routeDetail.getOrderNo();
        this.placeId = routeDetail.getPlaceId();
        this.placeType = routeDetail.getPlaceType();
    }
}
