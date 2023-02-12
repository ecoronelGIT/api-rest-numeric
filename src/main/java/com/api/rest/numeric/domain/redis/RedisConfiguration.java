package com.api.rest.numeric.domain.redis;

import javax.annotation.PostConstruct;

import com.api.rest.numeric.common.util.CacheUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisConfiguration {

  private String host;
  private int port;

  public RedisConfiguration(
      @Value("${spring.redis.host}") String host,
      @Value("${spring.redis.port}") int port)
  {
    this.host = host;
    this.port = port;
  }

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
    redisConfig.setHostName(host);
    redisConfig.setPort(port);
    return new JedisConnectionFactory(redisConfig);
  }

  @PostConstruct
  public void postConstruct() {
    CacheUtil.setCacheProvider(new SpringCacheProvider(jedisConnectionFactory()));
  }
}
