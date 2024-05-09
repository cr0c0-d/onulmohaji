package me.croco.onulmohaji.popupstore.repository;

import me.croco.onulmohaji.popupstore.domain.Popupstore;

import java.time.LocalDate;
import java.util.List;

public interface PopupstoreQueryDSLRepository {
    public List<Popupstore> findPopupstoreListByDate(LocalDate date);
}
