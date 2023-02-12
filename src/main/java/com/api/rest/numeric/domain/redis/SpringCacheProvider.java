package com.api.rest.numeric.domain.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

public class SpringCacheProvider {

  private StringRedisTemplate redisTemplate;

  public SpringCacheProvider(RedisConnectionFactory connectionFactory) {
    this.redisTemplate = new StringRedisTemplate(connectionFactory);
  }

  public SpringCacheProvider(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public Optional<String> get(String key) {
    return Optional.ofNullable(redisTemplate.boundValueOps(key).get());
  }

  public Boolean set(String key, String value) {
    redisTemplate.boundValueOps(key).set(value);
    return true;
  }

  public Boolean set(long seconds, String key, String value) {
    redisTemplate.boundValueOps(key).set(value, seconds, TimeUnit.SECONDS);
    return true;
  }

  public Boolean delete(String key) {
    return redisTemplate.delete(key);
  }

  public Boolean exists(String key ) {
    return Optional.ofNullable(redisTemplate.boundValueOps(key).get()).isEmpty();
  }

  public Optional<Long> incrementCounter(String counterName, Long units) {
    return Optional.ofNullable(redisTemplate.boundValueOps(counterName).increment(units));
  }

  public Optional<Long> decrementCounter(String counterName, Long units) {
    return Optional.ofNullable(redisTemplate.boundValueOps(counterName).increment(-units));
  }

}
