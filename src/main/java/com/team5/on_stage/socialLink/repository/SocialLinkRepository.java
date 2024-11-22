package com.team5.on_stage.socialLink.repository;

import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.socialLink.entity.SocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SocialLinkRepository  extends JpaRepository<SocialLink, Long> {

    @Query("SELECT new com.team5.on_stage.socialLink.dto.SocialLinkDTO(s.instagram, s.youtube, s.x, s.spotify, s.userId) " +
            "FROM SocialLink s WHERE s.userId = :userId")
    SocialLinkDTO findByUserId(@Param("userId") Long userId);
}
