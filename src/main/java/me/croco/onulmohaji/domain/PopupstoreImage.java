package me.croco.onulmohaji.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PopupstoreImage {

    @Id
    @Column(name = "store_image_id")
    private Long storeImageId;

    @Column(name = "store_id")
    private Long storeId;

    @Column
    private String url;
}
