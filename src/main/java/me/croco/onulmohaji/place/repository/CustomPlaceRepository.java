package me.croco.onulmohaji.place.repository;

import me.croco.onulmohaji.place.domain.CustomPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomPlaceRepository extends JpaRepository<CustomPlace, Long> {
}
