//package com.team5.on_stage.global.config.auth.refresh;
//
//import jakarta.transaction.Transactional;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface RefreshRepository extends JpaRepository<Refresh, Long> {
//
//    Boolean existsByRefresh(String refresh);
//
//    @Transactional
//    void deleteByRefresh(String refresh);
//}
