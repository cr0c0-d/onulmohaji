package me.croco.onulmohaji.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import me.croco.onulmohaji.domain.Exhibition;

import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ExhibitionListFindResponse {
    int totalCount;	    //전체count	9	1
    String from;    //	시작기간 	8	1	20100101
    String to;  //	종료기간 	8	1	20101201
    int cPage;  //	현재페이지	10	1	1
    int rows;   //	페이지당ROW수	3	1	10	3~100
    String place;   //	장소	50	0	1
    String gpsxfrom;    //	경도 하한	11	0	129.1013129	경도 범위검색 중 하한
    String gpsyfrom;    //	위도 하한	11	0	35.1416412	위도 범위검색 중 하한
    String gpsxto;  //	경도 상한	11	0	129.1013129	경도 범위검색 중 상한
    String gpsyto;  //	위도 상한	11	0	35.1416412	위도 범위검색 중 상한
    String keyword; //	검색키워드	20	0

    List<Exhibition> perforList;
}
