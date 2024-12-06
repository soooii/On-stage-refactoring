package com.team5.on_stage.global.config.redis;

//import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash(value = "redis_refresh")
public class RedisRefresh {

    @Id
    @Indexed
    private String refreshToken;

    // 사용자 식별
    private String username;

    @TimeToLive
    private Long timeToLive;

    @Builder
    public RedisRefresh(String username,
                        String refreshToken) {

        this.username = username;
        this.refreshToken = refreshToken;
        this.timeToLive = 1000L * 60 * 60 * 24 * 1;
    }
}
