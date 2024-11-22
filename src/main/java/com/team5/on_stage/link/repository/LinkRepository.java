package com.team5.on_stage.link.repository;

import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("""
        SELECT new com.team5.on_stage.link.dto.LinkDTO(
            l.id,
            l.thumbnail,
            l.title,
            l.priority,
            l.layout,
            l.active,
            (SELECT new com.team5.on_stage.linkDetail.dto.LinkDetailDTO(ld.id, ld.platform, ld.url)
             FROM LinkDetail ld
             WHERE ld.link.id = l.id)
        )
        FROM Link l
        WHERE l.userId = :userId
    """)
    List<LinkDTO> findAllByUserId(Long userId);
}
