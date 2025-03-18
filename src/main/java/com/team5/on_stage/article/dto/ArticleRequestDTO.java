package com.team5.on_stage.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequestDTO {
    private Long id;
    private String title;
    private String content;
    private String link;
    private String time;
    private boolean isDeleted;
    private String status;
}

