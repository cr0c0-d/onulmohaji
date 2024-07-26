package me.croco.onulmohaji.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class MemberSearchInfo {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "localcode_id")
    private Long localcodeId = Long.parseLong("11680");

    @Column
    private int distance = 3000;

    @Column(name = "category_filter")
    private String categoryFilter = "{}";
    
    public MemberSearchInfo(Long id, Long localcodeId, int distance, String categoryFilter) {
        this.id = id;
        this.localcodeId = localcodeId;
        this.distance = distance;
        this.categoryFilter = categoryFilter;
    }

    public MemberSearchInfo(Long id) {
        this.id = id;
    }
}
