package me.croco.onulmohaji.repository;

import me.croco.onulmohaji.domain.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long>, ExhibitionQueryDSLRepository {
}
