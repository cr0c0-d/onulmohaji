package me.croco.onulmohaji.exhibition.repository;

import me.croco.onulmohaji.exhibition.domain.Exhibition;

import java.util.List;

public interface ExhibitionQueryDSLRepository {
    public List<Exhibition> findExhibitionListByDate(String date);
}
