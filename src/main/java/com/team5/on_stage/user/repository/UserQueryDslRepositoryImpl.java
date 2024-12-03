package com.team5.on_stage.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.team5.on_stage.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryDslRepositoryImpl implements UserQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User findByUsername(String username) {
        return jpaQueryFactory
                .select(user)
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();
    }

    @Override
    public User findByNickname(String nickname) {
        return jpaQueryFactory
                .select(user)
                .from(user)
                .where(user.nickname.eq(nickname))
                .fetchOne();
    }

    @Override
    public Boolean deleteUserByUsername(String username) {
        return null;
    }

    @Override
    public Boolean existsByNickname(String nickname) {
        return null;
    }
}
