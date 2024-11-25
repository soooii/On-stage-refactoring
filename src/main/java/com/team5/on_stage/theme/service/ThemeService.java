package com.team5.on_stage.theme.service;

import com.team5.on_stage.global.config.s3.S3Uploader;
import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.theme.dto.ThemeDTO;
import com.team5.on_stage.theme.entity.Theme;
import com.team5.on_stage.theme.repository.ThemeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final S3Uploader s3Uploader;

    public ThemeDTO findByUserId(Long userId) {
        return themeRepository.findThemeDTOByUserId(userId);
    }

    @Transactional
    public ThemeDTO updateTheme(ThemeDTO themeDTO) {
        Theme target = themeRepository.findByUserId(themeDTO.getUserId())
                .orElseThrow(() -> new GlobalException(ErrorCode.THEME_NOT_FOUND));
        target.setBorderRadius(themeDTO.getBorderRadius());
        target.setButtonColor(themeDTO.getButtonColor());
        target.setProfileColor(themeDTO.getProfileColor());
        target.setFontColor(themeDTO.getFontColor());
        target.setIconColor(themeDTO.getIconColor());
        themeRepository.save(target);
        return themeDTO;
    }

    @Transactional
    public ThemeDTO updateBackgroundImage(Long userId, MultipartFile backgroundImage) throws IOException {
        Theme theme = themeRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.THEME_NOT_FOUND));

        String imageUrl = s3Uploader.upload(backgroundImage, "backgroundImages");
        theme.setBackgroundImage(imageUrl);
        themeRepository.save(theme);
        return themeRepository.findThemeDTOByUserId(userId);
    }
}
