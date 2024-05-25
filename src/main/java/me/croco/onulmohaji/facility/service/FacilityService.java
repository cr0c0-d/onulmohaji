package me.croco.onulmohaji.facility.service;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.facility.domain.Facility;
import me.croco.onulmohaji.facility.repository.FacilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public List<Facility> saveAll(List<Facility> facilityList) {
        return facilityRepository.saveAll(facilityList);
    }

    public List<Facility> findFoodListByPlace(Double latitude, Double longitude) {
        return facilityRepository.findFoodListByPlace(latitude, longitude);
    }
}
