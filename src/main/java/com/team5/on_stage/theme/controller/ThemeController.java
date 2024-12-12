package com.team5.on_stage.theme.controller;

import com.team5.on_stage.global.config.jwt.TokenUsername;
import com.team5.on_stage.theme.dto.ThemeDTO;
import com.team5.on_stage.theme.service.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/theme")
@RequiredArgsConstructor
@Tag(name = "Theme Controller", description = "테마 컨트롤러")
public class ThemeController {
    private final ThemeService themeService;

    @PutMapping
    @Operation(summary = "테마 수정", description = "테마를 수정")
    public ResponseEntity<ThemeDTO> updateTheme(@RequestBody ThemeDTO themeDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(themeService.updateTheme(themeDTO));
    }

    @Operation(summary = "배경 이미지 업로드", description = "배경 이미지를 업로드")
    @PutMapping("/background")
    public ResponseEntity<ThemeDTO> uploadBackgroundImage(@TokenUsername String username, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(themeService.updateBackgroundImage(username, file));
    }

    @Operation(summary = "배경 이미지 클리어", description = "배경 이미지를 클리어")
    @PutMapping("/background/clear")
    public ResponseEntity<ThemeDTO> clearBackgroundImage(@TokenUsername String username) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(themeService.clearBackgroundImage(username));
    }
}
