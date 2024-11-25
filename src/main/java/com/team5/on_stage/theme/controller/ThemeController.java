package com.team5.on_stage.theme.controller;

import com.team5.on_stage.theme.dto.ThemeDTO;
import com.team5.on_stage.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/theme")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;


    @PutMapping
    public ResponseEntity<ThemeDTO> updateTheme(@RequestBody ThemeDTO themeDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(themeService.updateTheme(themeDTO));
    }
}
