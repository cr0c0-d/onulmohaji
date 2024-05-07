package me.croco.onulmohaji.service;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.dto.PopplyPopupstoreFindResponse;
import me.croco.onulmohaji.domain.Popupstore;
import me.croco.onulmohaji.domain.PopupstoreImage;
import me.croco.onulmohaji.repository.PopupstoreDetailRepository;
import me.croco.onulmohaji.repository.PopupstoreImageRepository;
import me.croco.onulmohaji.repository.PopupstoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PopupstoreService {

    private final PopupstoreRepository popupstoreRepository;
    private final PopupstoreDetailRepository popupstoreDetailRepository;
    private final PopupstoreImageRepository popupstoreImageRepository;

    // 팝업스토어 저장
    public void savePopupstoreInfo(List<PopplyPopupstoreFindResponse> storeList) {
        storeList.forEach(response -> {
            Popupstore popupstore = new Popupstore(response);
            popupstoreRepository.save(popupstore);
            popupstoreDetailRepository.save(response.getStoreDetail());
            response.getStoreImage().forEach(popupstoreImageRepository::save);
        });
    }

    public List<Popupstore> findPopupstoreListByDate(String date) {
        return popupstoreRepository.findPopupstoreListByDate(LocalDate.parse((CharSequence) date));
    }
}
