package com.team5.on_stage.concert.repository;

import com.team5.on_stage.concert.entity.ConcertPlace;
import org.springframework.data.jpa.repository.JpaRepository;

// pk타입 적기
public interface ConcertPlaceRepository extends JpaRepository<ConcertPlace,Long> {
}
