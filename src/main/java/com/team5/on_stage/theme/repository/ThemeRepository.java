package com.team5.on_stage.theme.repository;

import com.team5.on_stage.theme.dto.ThemeDTO;
import com.team5.on_stage.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query("SELECT new com.team5.on_stage.theme.dto.ThemeDTO(t.userId, t.backgroundImage, t.buttonColor, t.profileColor, t.fontColor,t.iconColor, t.borderRadius ) " +
            "FROM Theme t WHERE t.userId = :userId" )
    ThemeDTO findThemeDTOByUserId(@Param("userId") Long userId);

    Optional<Theme> findByUserId(Long userId);
}
