package com.team5.on_stage.link.repository;

import com.team5.on_stage.link.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<List<Link>> findByUserId(Long userId);

    Optional<Link> findById(Long id);
}
