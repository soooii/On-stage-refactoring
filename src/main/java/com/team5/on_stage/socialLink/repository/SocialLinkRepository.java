package com.team5.on_stage.socialLink.repository;

import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.socialLink.entity.SocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialLinkRepository  extends JpaRepository<SocialLink, Long> {

    @Query("SELECT new com.team5.on_stage.socialLink.dto.SocialLinkDTO(s.instagram, s.youtube, s.x, s.spotify, s.linkId) " +
            "FROM SocialLink s WHERE s.linkId = :linkId")
    SocialLinkDTO findByLinkId(@Param("linkId") Long linkId);
}
