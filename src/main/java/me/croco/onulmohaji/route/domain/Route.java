package me.croco.onulmohaji.route.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "route_date")
    private LocalDate routeDate;

    @Column(name = "like_cnt")
    private int likeCnt;

    @Column(name = "share_type")
    private int shareType;

    @Column
    private int valid;


}
