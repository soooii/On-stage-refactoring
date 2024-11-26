package com.team5.on_stage.linkDetail.repository;

import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import com.team5.on_stage.linkDetail.entity.LinkDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkDetailRepository extends JpaRepository<LinkDetail, Long> {

    @Query("SELECT new com.team5.on_stage.linkDetail.dto.LinkDetailDTO(ld.id, ld.platform, ld.url) " +
            "FROM LinkDetail ld " +
            "WHERE ld.link.id = :linkId AND ld.isDeleted = false")
    List<LinkDetailDTO> findByLinkId(@Param("linkId") Long linkId);


    @Modifying
    @Transactional
    @Query("UPDATE LinkDetail ld SET ld.isDeleted = true WHERE ld.link.id = :linkId")
    void softDeleteByLinkId(Long linkId);



    @Modifying
    @Query("UPDATE LinkDetail  ld SET ld.isDeleted = true  WHERE ld.id = :id")
    void softDeleteById(Long id);

    //void deleteAllByLinkId(Long linkId);
}
