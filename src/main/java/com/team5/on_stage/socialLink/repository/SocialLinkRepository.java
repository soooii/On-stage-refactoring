package com.team5.on_stage.socialLink.repository;

import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.socialLink.entity.SocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SocialLinkRepository  extends JpaRepository<SocialLink, Long> {
    // Optional<SocialLink> findByUserId(Long userId);

    @Query("SELECT new com.team5.on_stage.socialLink.dto.SocialLinkDTO(s.username, s.instagram, s.youtube, s.x, s.spotify, s.github) " +
            "FROM SocialLink s WHERE s.username = :username")
    Optional<SocialLinkDTO> getSocial(@Param("username") String username);

    @Modifying
    @Query("UPDATE SocialLink sl SET sl.instagram = :instagram, sl.youtube = :youtube, sl.x = :x, sl.spotify = :spotify, sl.github = :github WHERE sl.username = :username")
    void updateSocial(
            @Param("username") String username,
            @Param("instagram") String instagram,
            @Param("youtube") String youtube,
            @Param("x") String x,
            @Param("spotify") String spotify,
            @Param("github") String github
    );
}
