package com.team5.on_stage.summary.entity;

import com.team5.on_stage.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
//@Where(clause = "is_deleted = false")
@Table(
        name = "summary",
        indexes = {
                @Index(name = "idx_created_at", columnList = "created_at DESC")
        }
)
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 id
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    //요약 제목
    private String title;

    //요약 뉴스
    @Lob
    @Column(name="summary", columnDefinition = "TEXT", nullable = false)
    private String summary;

    //삭제 여부
    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
