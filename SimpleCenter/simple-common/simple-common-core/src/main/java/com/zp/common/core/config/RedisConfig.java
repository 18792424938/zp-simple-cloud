package com.zp.common.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.open: false}")
    private boolean open = false;

    //数据库数量
    @Value("${spring.redis.database:1}")
    private int database;
    @Value("${spring.redis.host:127.0.0.1}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.password:123456}")
    private String password;

    //连接超时时间 毫秒
    @Value("${spring.redis.timeout:5000}")
    private int timeout;

    //最大等待时间 毫秒
    @Value("${spring.redis.jedis.pool.max-wait:1000}")
    private int maxWait;

    //最大连接数
    @Value("${spring.redis.jedis.pool.max-total:100}")
    private int maxTotal;

    //最大空闲数
    @Value("${spring.redis.jedis.pool.max-idle:20}")
    private int maxIdle;

    //最小空闲数
    @Value("${spring.redis.jedis.pool.min-idle:1}")
    private int minIdle;

    @Bean
    public JedisPool  getRedis(){

        JedisPool jedisPool = null;

        if(open){
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMinIdle(minIdle);//最小空闲数
            jedisPoolConfig.setMaxIdle(maxIdle);//最大空闲数
            jedisPoolConfig.setMaxWaitMillis(maxWait);//存取最大等待毫秒数
            jedisPoolConfig.setMaxTotal(maxTotal);
            // 连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
            jedisPoolConfig.setBlockWhenExhausted(true);
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout,password,database);
        }

        return jedisPool;
    }


}
