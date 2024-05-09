package me.croco.onulmohaji.popupstore.service;

import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.api.dto.PopplyPopupstoreFindResponse;
import me.croco.onulmohaji.popupstore.domain.Popupstore;
import me.croco.onulmohaji.popupstore.domain.PopupstoreDetail;
import me.croco.onulmohaji.popupstore.domain.PopupstoreImage;
import me.croco.onulmohaji.popupstore.repository.PopupstoreDetailRepository;
import me.croco.onulmohaji.popupstore.repository.PopupstoreImageRepository;
import me.croco.onulmohaji.popupstore.repository.PopupstoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public Popupstore findPopupstoreById(String id) {
        return popupstoreRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 popupstore id : " + id));
    }
    public PopupstoreDetail findPopupstoreDetailById(String id) {
        return popupstoreDetailRepository.findByStoreId(Long.valueOf(id)).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 popupstore id : " + id));
    }

    public List<PopupstoreImage> findPopupstoreImagesById(String id) {
        return popupstoreImageRepository.findByStoreId(Long.valueOf(id));
    }
}
