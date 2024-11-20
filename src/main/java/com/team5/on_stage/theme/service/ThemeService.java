package com.team5.on_stage.theme.service;

import com.team5.on_stage.theme.dto.ThemeDTO;
import com.team5.on_stage.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeDTO findByLinkId(Long linkId) {
        return themeRepository.findThemeByLinkId(linkId);
    }
}
