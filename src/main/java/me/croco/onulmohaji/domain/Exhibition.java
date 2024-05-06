package me.croco.onulmohaji.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Exhibition {
    private Long seq;   //	일련번호	9	1	12402
    private String title;   //	제목	200	1	칼라바 쇼
    private String startDate;   //	시작일	8	1	20100101
    private String endDate; //	마감일	8	1	20100107
    private String place;    //	장소	50	1	폴리미디어 씨어터
    private String realmName;    //	분류명	10	1	연극
    private String area;    //	지역	10	0	서울
    private String thumbnail;   //	썸네일	256	1
    private String gpsX; //	GPS-X좌표	11	0	129.1013129	경도
    private String gpsY;    //	GPS-Y좌표	11	0	35.1416412	위도

}
