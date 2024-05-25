package me.croco.onulmohaji.facility.repository;

import me.croco.onulmohaji.facility.domain.Facility;

import java.util.List;

public interface FacilityQueryDSLRepository {
    List<Facility> findFoodListByPlace(Double latitude, Double longitude);
}
