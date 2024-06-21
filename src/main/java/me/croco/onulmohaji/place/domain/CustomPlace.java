package me.croco.onulmohaji.place.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class CustomPlace {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column
    private String name;

    @Column
    private String address;

    @Column(name="address_road")
    private String addressRoad;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Builder
    public CustomPlace(Long userId, String name, String address, String addressRoad, Double latitude, Double longitude) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.addressRoad = addressRoad;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
