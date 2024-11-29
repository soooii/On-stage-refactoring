package com.team5.on_stage.util.pagination.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationRequestDTO {
    private int page = 0; // 기본값 설정
    private int size = 2; // 한 페이지당 보여줄 데이터 개수

    public PaginationRequestDTO(int page, int size) {
        this.page = Math.max(0, page); //페이지 번호 0 이하 X
        this.size = Math.max(1, size); //한 페이지에 최소 하나의 데이터
    }
}