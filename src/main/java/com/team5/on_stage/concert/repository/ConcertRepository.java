package com.team5.on_stage.concert.repository;

import com.team5.on_stage.concert.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends JpaRepository<Concert,Long> {
}
