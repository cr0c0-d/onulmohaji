package me.croco.onulmohaji.route.dto;

import lombok.Getter;
import lombok.Setter;
import me.croco.onulmohaji.route.domain.Route;
import me.croco.onulmohaji.route.domain.RouteDetail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
@Getter
public class RouteFindResponse {
    private Long routeId;

    private Long userId;

    private String routeDate;

    private int likeCnt;

    private int shareType;

    private int valid;

    private List<RouteDetailFindResponse> routeDetailList;

    public RouteFindResponse(Route route, List<RouteDetailFindResponse> routeDetailList) {
        this.routeId = route.getId();
        this.userId = route.getUserId();
        this.routeDate = route.getRouteDate();
        this.likeCnt = route.getLikeCnt();
        this.shareType = route.getShareType();
        this.valid = route.getValid();
        this.routeDetailList = routeDetailList;
    }
}
