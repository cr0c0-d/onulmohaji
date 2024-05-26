package me.croco.onulmohaji.facility.service;

import com.querydsl.core.Tuple;
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
        List<Tuple> list = facilityRepository.findFoodListByPlace(latitude, longitude);
        return list.stream().map(tuple -> {
            Facility facility = tuple.get(0, Facility.class);
            Double distance = tuple.get(1, Double.class);

            facility.setDistance((int) Math.floor(distance*1000));
            return facility;
        }).toList();
    }
}
