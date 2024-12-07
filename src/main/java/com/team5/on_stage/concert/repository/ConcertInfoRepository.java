package com.team5.on_stage.concert.repository;

import com.team5.on_stage.concert.entity.ConcertInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertInfoRepository extends JpaRepository<ConcertInfo, String> {
    // concert_nm으로 검색
    @EntityGraph(attributePaths = {
        "concertDetail.concertPlace",
                "concertDetail.relate"
    })
    List<ConcertInfo> findByPrfnmContaining(String concertNm);
    boolean existsByMt20id(String mt20id); // 유니크 키 확인 메서드
}