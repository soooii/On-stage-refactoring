package com.team5.on_stage.theme.repository;

import com.team5.on_stage.theme.dto.ThemeDTO;
import com.team5.on_stage.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query("SELECT new com.team5.on_stage.theme.dto.ThemeDTO(t.backgroundImage, t.preferColor, t.fontColor, t.borderRadius, t.linkId) " +
            "FROM Theme t WHERE t.linkId = :linkId" )
    ThemeDTO findThemeByLinkId(@Param("linkId") Long linkId);
}
