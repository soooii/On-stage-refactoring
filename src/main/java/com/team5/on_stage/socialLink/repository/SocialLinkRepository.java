package com.team5.on_stage.socialLink.repository;

import com.team5.on_stage.socialLink.entity.SocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialLinkRepository  extends JpaRepository<SocialLink, Long> {

}
