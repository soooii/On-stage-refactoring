package com.team5.on_stage.user.repository;

import com.team5.on_stage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이미 생성된 사용자인지, 최초 가입 사용자인지 판단
    // Todo: 확인을 username으로 하는 게 좋을 것 같다.
    Boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);


    Optional<User> findByEmail(String email);


    Boolean deleteUserByEmail(String email);
}
