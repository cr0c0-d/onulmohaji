package me.croco.onulmohaji.route.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoutePermissionInfoResponse {
    private Long routeId;
    private String routeDate;
    private String ownerName;
}
