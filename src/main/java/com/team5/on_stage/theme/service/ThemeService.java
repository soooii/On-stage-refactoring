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
@Transactional
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final S3Uploader s3Uploader;

    // READ
    @Transactional(readOnly = true)
    public ThemeDTO getTheme(Long userId) {
        return themeRepository.getTheme(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.THEME_NOT_FOUND));
    }

    // CREATE (user 생성 시점에 같이 생성)
    public void createTheme(Long userId) {
        themeRepository.save(new Theme(userId));
    }

    // UPDATE
    public ThemeDTO updateTheme(ThemeDTO dto) {
        themeRepository.updateTheme(
                dto.getUserId(),
                dto.getBorderRadius(),
                dto.getButtonColor(),
                dto.getProfileColor(),
                dto.getFontColor(),
                dto.getIconColor()
        );
        return dto;
    }

    public ThemeDTO updateBackgroundImage(Long userId, MultipartFile backgroundImage) throws IOException {
        Theme theme = themeRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.THEME_NOT_FOUND));

        String imageUrl = s3Uploader.upload(backgroundImage, "backgroundImages");
        theme.setBackgroundImage(imageUrl);
        themeRepository.save(theme);
        return getTheme(userId);
    }
}
