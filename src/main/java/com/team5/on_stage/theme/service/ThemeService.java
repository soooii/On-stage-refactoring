package com.team5.on_stage.theme.service;

import com.team5.on_stage.global.config.s3.S3Uploader;
import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.theme.dto.ThemeDTO;
import com.team5.on_stage.theme.entity.Theme;
import com.team5.on_stage.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final S3Uploader s3Uploader;

    // READ
    public ThemeDTO getTheme(String username) {
        return themeRepository.getTheme(username)
                .orElseThrow(() -> new GlobalException(ErrorCode.THEME_NOT_FOUND));
    }

    // CREATE (user 생성 시점에 같이 생성)
    public void createTheme(String username) {
        themeRepository.save(new Theme(username));
    }

    // UPDATE
    @Transactional
    public ThemeDTO updateTheme(ThemeDTO dto) {
        themeRepository.updateTheme(
                dto.getUsername(),
                dto.getBorderRadius(),
                dto.getButtonColor(),
                dto.getProfileColor(),
                dto.getFontColor(),
                dto.getIconColor(),
                dto.getBackgroundColor()
        );
        return dto;
    }

    // IMAGE UPDATE
    @Transactional
    public ThemeDTO updateBackgroundImage(String username, MultipartFile backgroundImage) throws IOException {
        Theme theme = themeRepository.findByUsername(username)
                .orElseThrow(() -> new GlobalException(ErrorCode.THEME_NOT_FOUND));

        String imageUrl = s3Uploader.upload(backgroundImage, "backgroundImages");
        theme.setBackgroundImage(imageUrl);
        themeRepository.save(theme);
        return getTheme(username);
    }

    public ThemeDTO clearBackgroundImage(String username) throws IOException {
        Theme theme = themeRepository.findByUsername(username)
                .orElseThrow(() -> new GlobalException(ErrorCode.THEME_NOT_FOUND));
        theme.setBackgroundImage(null);
        themeRepository.save(theme);
        return getTheme(username);
    }
}
