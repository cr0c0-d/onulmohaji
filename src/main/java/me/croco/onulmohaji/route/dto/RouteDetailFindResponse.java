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

    private int orderNo;

    private Long placeId;

    private String placeType;

    public RouteDetailFindResponse(RouteDetail routeDetail) {
        this.orderNo = routeDetail.getOrderNo();
        this.placeId = routeDetail.getPlaceId();
        this.placeType = routeDetail.getPlaceType();
    }
}
