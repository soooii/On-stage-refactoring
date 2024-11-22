package com.team5.on_stage.linkDetail.repository;

import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import com.team5.on_stage.linkDetail.entity.LinkDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkDetailRepository extends JpaRepository<LinkDetail, Long> {


    @Query("SELECT new com.team5.on_stage.linkDetail.dto.LinkDetailDTO(ld.id, ld.platform, ld.url) " +
            "FROM LinkDetail ld " +
            "WHERE ld.link.id = :linkId")
    List<LinkDetailDTO> findLinkDetailsByLinkId(@Param("linkId") Long linkId);
}
