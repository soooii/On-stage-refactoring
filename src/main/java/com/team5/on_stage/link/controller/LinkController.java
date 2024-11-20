package com.team5.on_stage.link.controller;

import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/link")
public class LinkController {

    private final LinkService linkService;

    // test 용 api (linkId 기반으로 탐색 -> 추후에는 userId 기반으로 탐색)
    @GetMapping
    public ResponseEntity<Link> getLink(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body( linkService.getLink(id));
    }


}
