package com.uin.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author dingchuan
 */
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisConnectionFactory redisConnectionFactory;
  private final ObjectMapper objectMapper;

  /**
   * 使用RedisTemplate<String, Object> 是需要注意其序列化的方式
   *
   * @param redisSerializer
   * @return
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisSerializer<Object> redisSerializer) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    // key的序列化
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(redisSerializer);
    // hash key
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    // hash value
    redisTemplate.setHashValueSerializer(redisSerializer);
    return redisTemplate;
  }

  /**
   * 更换redis的序列化形式为Jackson
   *
   * @return
   */
  @Bean
  public RedisSerializer<Object> redisSerializer() {
    // 创建JSON序列化器
    Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
    serializer.setObjectMapper(objectMapper);
    return serializer;
  }
}
