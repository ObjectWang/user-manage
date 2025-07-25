package org.example.user.manage.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration

public class RedisConfig {

    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // key采用String的序列化方式
        template.setKeySerializer(new StringRedisSerializer());

        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setConnectionFactory(factory);

        return template;
    }

}