package com.team5.on_stage.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //기사 제목
    @Column(name="title", nullable = false)
    private String title;

    //기사 내용
    @Lob
    @Column(name="content",nullable = false)
    private String content;

    //기사 링크
    @Column(name="link", nullable = false)
    private String link;
}