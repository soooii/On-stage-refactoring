package com.team5.on_stage.user.repository;

import com.team5.on_stage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이미 생성된 사용자인지, 최초 가입 사용자인지 판단
    Boolean existsByEmail(String email);


    Boolean deleteUserByEmail(String email);
}
