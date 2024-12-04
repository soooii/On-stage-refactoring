package com.team5.on_stage.subscribe.repository;

import com.team5.on_stage.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long>, SubscribeQueryDslRepository {
}
