package com.team5.on_stage.summary.dto;

import com.team5.on_stage.util.pagination.dto.PaginationRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryRequestDTO extends PaginationRequestDTO {
    private String username;

    public SummaryRequestDTO(String username, int page, int size){
        super(page, size);
        this.username = username;
    }
}
