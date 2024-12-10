package com.team5.on_stage.subscribe.repository;

import com.team5.on_stage.subscribe.entity.Subscribe;
import com.team5.on_stage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long>, SubscribeQueryDslRepository {

    List<Subscribe> findAllBySubscriber(User subscriber);
}
