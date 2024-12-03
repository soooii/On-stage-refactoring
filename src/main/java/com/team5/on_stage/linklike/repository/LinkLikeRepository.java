package com.team5.on_stage.linklike.repository;

import com.team5.on_stage.linklike.entity.LinkLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkLikeRepository extends JpaRepository<LinkLike, Long> {

    Boolean existsLinkLikeByUserIdAndLinkId(Long userId, Long linkId);

    LinkLike findLinkLikeByUserIdAndLinkId(Long userId, Long linkId);

    void deleteLinkLikeByUserIdAndLinkId(Long userId, Long linkId);
}
