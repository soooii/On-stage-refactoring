package com.team5.on_stage.concert.repository;

import com.team5.on_stage.concert.entity.ConcertDetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ConcertDetailRepository extends JpaRepository<ConcertDetail, String> {
    // performer로 검색
    @EntityGraph(attributePaths = {
        "concertDetail.concertPlace",
                "concertDetail.relate"
    })
    List<ConcertDetail> findByPrfcastContaining(String performer);
}