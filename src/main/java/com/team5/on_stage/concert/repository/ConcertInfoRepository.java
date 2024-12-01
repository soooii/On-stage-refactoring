package com.team5.on_stage.concert.repository;

import com.team5.on_stage.concert.entity.ConcertInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertInfoRepository extends JpaRepository<ConcertInfo,String> {
}
