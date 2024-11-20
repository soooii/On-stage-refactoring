package com.team5.on_stage.link.repository;

import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT new com.team5.on_stage.link.dto.LinkDTO(l.thumbnail, l.title, l.description, l.priority, l.layout, l.active) " +
            "FROM Link l WHERE l.userId = :userId")
    LinkDTO findAllByUserId(@Param("linkId") Long userId);
}
