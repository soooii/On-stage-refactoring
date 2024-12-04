package com.team5.on_stage.concert.repository;

import com.team5.on_stage.concert.entity.Relate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelateRepository extends JpaRepository<Relate,Long> {
    boolean existsByMt20id(String mt20id); // 유니크 키 확인 메서드
}
