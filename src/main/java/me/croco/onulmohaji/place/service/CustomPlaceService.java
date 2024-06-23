package me.croco.onulmohaji.place.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.place.domain.CustomPlace;
import me.croco.onulmohaji.place.dto.CustomPlaceAddRequest;
import me.croco.onulmohaji.place.repository.CustomPlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomPlaceService {

    private final CustomPlaceRepository customPlaceRepository;

    public CustomPlace addCustomPlace(CustomPlaceAddRequest addRequest, Member loginMember) {
        return customPlaceRepository.save(CustomPlace.builder()
                .userId(loginMember.getId())
                .name(addRequest.getName())
                .address(addRequest.getAddress())
                .addressRoad(addRequest.getAddressRoad())
                .latitude(addRequest.getLatitude())
                .longitude(addRequest.getLongitude())
                .build());
    }

    public List<CustomPlace> findCustomPlaceListByUserId(Long userId) {
        return customPlaceRepository.findCustomPlaceListByUserId(userId);
    }

}
