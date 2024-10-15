package com.in2it.rediscachingdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.in2it.rediscachingdemo.dto.ProductDto;

@Configuration
public class RedisConfig {

	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
	    return new JedisConnectionFactory();
	}
	
//	@Bean
//	public RedisTemplate<String, ProductDto> redisTemplate(RedisConnectionFactory connectionFactory) {
//		RedisTemplate<String, ProductDto> template = new RedisTemplate<>();
//		template.setConnectionFactory(connectionFactory);
//
//		Jackson2JsonRedisSerializer<ProductDto> serializer = new Jackson2JsonRedisSerializer<>(ProductDto.class);
//		template.setDefaultSerializer(serializer);
//
//		return template;
//	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplateTwo() {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new JdkSerializationRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		template.setEnableTransactionSupport(true);
		template.afterPropertiesSet();
	    return template;
	}
}
