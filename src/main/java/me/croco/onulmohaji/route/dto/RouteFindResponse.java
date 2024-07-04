package me.croco.onulmohaji.route.dto;

import lombok.Getter;
import lombok.Setter;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.route.domain.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class RouteFindResponse {
    private Long routeId;

    private Long userId;

    private String title;

    private String routeDate;

    private int likeCnt;

    private int shareType;

    private int valid;

    private List<RouteDetailFindResponse> routeDetailList;

    private List<String> routeMapUrlList;

    private Map<Long, String> memberList;

    public RouteFindResponse(Route route, List<RouteDetailFindResponse> routeDetailList, List<String> routeMapUrlList, List<Member> memberList) {
        this.routeId = route.getId();
        this.userId = route.getUserId();
        this.title = route.getTitle();
        this.routeDate = route.getRouteDate();
        this.likeCnt = route.getLikeCnt();
        this.shareType = route.getShareType();
        this.valid = route.getValid();
        this.routeDetailList = routeDetailList;
        this.routeMapUrlList = routeMapUrlList;
        Map<Long, String> memberMap = new HashMap<>();
        memberList.forEach(member -> memberMap.put(member.getId(), member.getNickname()));
        this.memberList = memberMap;

    }
}
