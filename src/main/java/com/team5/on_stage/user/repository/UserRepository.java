package com.team5.on_stage.user.repository;

import com.team5.on_stage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Boolean deleteUserByUsername(String username);

    Boolean existsByNickname(String nickname);
}
