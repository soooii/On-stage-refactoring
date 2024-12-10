package com.team5.on_stage.theme.controller;

import com.team5.on_stage.global.config.jwt.TokenUsername;
import com.team5.on_stage.theme.dto.ThemeDTO;
import com.team5.on_stage.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/theme")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;


    @PutMapping
    public ResponseEntity<ThemeDTO> updateTheme(@RequestBody ThemeDTO themeDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(themeService.updateTheme(themeDTO));
    }

    @PutMapping("/background")
    public ResponseEntity<ThemeDTO> uploadBackgroundImage(@TokenUsername String username, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(themeService.updateBackgroundImage(username, file));
    }

    @PutMapping("/background/clear")
    public ResponseEntity<ThemeDTO> clearBackgroundImage(@TokenUsername String username) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(themeService.clearBackgroundImage(username));
    }
}
