package me.croco.onulmohaji.repository;

import me.croco.onulmohaji.domain.Popupstore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopupstoreRepository extends JpaRepository<Popupstore, Long>, PopupstoreQueryDSLRepository {
}
