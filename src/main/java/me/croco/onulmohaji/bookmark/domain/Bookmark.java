package me.croco.onulmohaji.bookmark.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "place_type", nullable = false)
    private String placeType;

    @Column(name = "place_id", nullable = false)
    private String placeId;

    @Builder
    public Bookmark(Long userId, String placeType, String placeId) {
        this.userId = userId;
        this.placeType = placeType;
        this.placeId = placeId;
    }

}