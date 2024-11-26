package com.team5.on_stage.link.repository;

import com.team5.on_stage.link.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    //Optional<List<Link>> findByUserId(Long userId);

    // 활성화된 링크 조회 (isDeleted = false)
    @Query("SELECT l FROM Link l WHERE l.userId = :userId AND l.isDeleted = false")
    Optional<List<Link>> findByUserId(Long userId);

    Optional<Link> findById(Long id);

    @Modifying
    @Query("UPDATE Link l SET l.isDeleted = true WHERE l.id = :id")
    void softDeleteById(Long id);

    @Modifying
    @Query("UPDATE Link l SET l.title = :title, l.prevLinkId = :prevLinkId, l.active = :active WHERE l.id = :id")
    void updateLink(
            @Param("title") String title,
            @Param("prevLinkId") Long prevLinkId,
            @Param("active") boolean active,
            @Param("id") Long id);
}
