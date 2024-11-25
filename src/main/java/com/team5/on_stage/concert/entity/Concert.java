package com.team5.on_stage.concert.entity;

import com.team5.on_stage.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "concert")
public class Concert {
    @Id
    @Column(name="seq", nullable = false)
    private long seq;
//    //유저 id
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @Column(name="realm_name", nullable = false)
    private String realmName;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="place", nullable = false)
    private String place;
    @Column(name="area", nullable = false)
    private String area;
    @Column(name="thumbnail")
    private String thumbnail;

    // TODO BigDecimal 확인 필요
    // 위도
    @Column(name="latitude", nullable = false)
    private double latitude;
    // 경도
    @Column(name="longtitude", nullable = false)
    private double longtitude;
    //시작
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private java.util.Date startDate;
    //마감
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private java.util.Date endDate;

    //공연자
    @Column(name="nickname")
    private String nickname;

    //업데이트 날짜
    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updatedAt;

}
