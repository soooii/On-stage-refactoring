package com.team5.on_stage.summary.entity;

import com.team5.on_stage.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="summary")
@SQLDelete(sql = "UPDATE summary SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //요약 뉴스
    private String summary;

    //삭제 여부
    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;
}
