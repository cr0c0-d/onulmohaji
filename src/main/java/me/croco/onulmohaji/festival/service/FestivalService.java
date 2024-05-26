package me.croco.onulmohaji.festival.service;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.KoreanFestivalService;
import me.croco.onulmohaji.api.dto.KoreanFestivalListFindResponse;
import me.croco.onulmohaji.festival.domain.Festival;
import me.croco.onulmohaji.festival.repository.FestivalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalService {

    private final KoreanFestivalService koreanFestivalService;
    private final FestivalRepository festivalRepository;

    public List<Festival> saveNewFestivalList() {
        List<KoreanFestivalListFindResponse> festivalList = koreanFestivalService.getFestivalList();
        return festivalRepository.saveAll(festivalList.stream().map(Festival::new).toList());
    }
}
