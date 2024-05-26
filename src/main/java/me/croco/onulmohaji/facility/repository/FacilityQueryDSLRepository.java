package me.croco.onulmohaji.facility.repository;

import com.querydsl.core.Tuple;
import me.croco.onulmohaji.facility.domain.Facility;

import java.util.List;

public interface FacilityQueryDSLRepository {
    List<Tuple> findFoodListByPlace(Double latitude, Double longitude);
}
