package com.team5.on_stage.analytic.repository;

import com.team5.on_stage.analytic.entity.LocationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationInfo, Long> {
    Optional<LocationInfo> findByIpAddress(String ipAddress); // IP 주소로 위치 정보 조회
}
