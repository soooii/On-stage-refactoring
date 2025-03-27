package com.team5.on_stage.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@EnableRedisRepositories
@Configuration
public class RedisConfig {

    private final RedisProperties redisProperties;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();

        redisConfiguration.setHostName(host);
        redisConfiguration.setPort(port);
        redisConfiguration.setPassword(password);

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration);

        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate
                .setKeySerializer(new StringRedisSerializer());
        redisTemplate
                .setValueSerializer(new StringRedisSerializer());
        redisTemplate
                .setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }

    // Todo - 직렬화
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}
