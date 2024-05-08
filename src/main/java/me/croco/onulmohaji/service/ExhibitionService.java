package me.croco.onulmohaji.service;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.domain.Exhibition;
import me.croco.onulmohaji.repository.ExhibitionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    public void saveExhibitionFromSearchResult(List<Exhibition> exhibitionList) {
        exhibitionList.forEach(exhibition -> {
            // yyyyMMdd 형식 문자열 -> yyyy-MM-dd 형식으로 바꿔 저장
            DateTimeFormatter before = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter after = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            exhibition.setStartDate(LocalDate.parse(exhibition.getStartDate(), before).format(after));
            exhibition.setEndDate(LocalDate.parse(exhibition.getEndDate(), before).format(after));
        });
        exhibitionRepository.saveAll(exhibitionList);
    }

    public List<Exhibition> findExhibitionListByDate(String date) {
        return exhibitionRepository.findExhibitionListByDate(date);
    }
}
