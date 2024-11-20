package com.team5.on_stage.link.controller;

import com.team5.on_stage.link.entity.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/link")
public class LinkController {


    // test 용 api
    @GetMapping
    public ResponseEntity<Link> getLink(Long id) {

    }


}
