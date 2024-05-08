package me.croco.onulmohaji.repository;

import me.croco.onulmohaji.domain.Exhibition;

import java.util.List;

public interface ExhibitionQueryDSLRepository {
    public List<Exhibition> findExhibitionListByDate(String date);
}
