package com.team5.on_stage.article.entity;

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
@Table(name = "article")
@Where(clause = "is_deleted = false")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    //기사 제목
    @Column(name="title", nullable = false)
    private String title;

    //기사 내용
    @Lob
    @Column(name="content", columnDefinition = "TEXT", nullable = false)
    private String content;

    //기사 링크
    @Column(name="link", nullable = false)
    private String link;

    //기사 발행 시간
    @Column(name="time", nullable = false)
    private String time;

    //삭제 여부
    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;

    //수동 검수 여부
    @Builder.Default
    @Enumerated(EnumType.STRING) // ENUM 값 문자열로 저장
    private ArticleStatus status = ArticleStatus.WAITING;

    // 검수 상태 업데이트
    public Article updateStatus(ArticleStatus status) {
        this.status = status;
        return this;
    }
}