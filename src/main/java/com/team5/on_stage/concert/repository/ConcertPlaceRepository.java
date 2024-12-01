package com.team5.on_stage.concert.repository;

import com.team5.on_stage.concert.entity.ConcertPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertPlaceRepository extends JpaRepository<ConcertPlace,String> {
}
