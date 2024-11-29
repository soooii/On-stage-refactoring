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

    //유저 id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
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

    //삭제 여부
    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;
}