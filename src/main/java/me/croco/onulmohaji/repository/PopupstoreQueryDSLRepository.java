package me.croco.onulmohaji.repository;

import me.croco.onulmohaji.domain.Popupstore;

import java.time.LocalDate;
import java.util.List;

public interface PopupstoreQueryDSLRepository {
    public List<Popupstore> findPopupstoreListByDate(LocalDate date);
}
