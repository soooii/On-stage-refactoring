package com.team5.on_stage.link.mapper;

import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.linkDetail.service.LinkDetailService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
@RequiredArgsConstructor
public class LinkMapper {
    private final LinkDetailService linkDetailService;



    public LinkDTO toDTO(Link link) {
        return LinkDTO.builder()
                .id(link.getId())
                .userId(link.getUserId())
                .title(link.getTitle())
                .prevLinkId(link.getPrevLinkId())
                .active(link.isActive())
                .details(linkDetailService.findByLinkId(link.getId()))
                .build();
    }

    public List<LinkDTO> toDTOList(List<Link> links) {
        return links.stream().map(this::toDTO).collect(Collectors.toList());
    }


}
