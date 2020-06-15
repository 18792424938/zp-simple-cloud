package com.zp.common.core.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Redis工具类
 *
 * @author zhaipan
 * 
 * @date 2017-07-17 21:12
 */
@Component
//@RefreshScope
public class RedisUtils {
    //是否开启redis缓存  true开启   false关闭
    @Value("${spring.redis.open: false}")
    private boolean open = false;

    @Autowired(required=false)
    private JedisPool jedisPool;
    
    Map<String, Object> cache = new ConcurrentHashMap<>();
    /**
     * 缓存过期配置
     * key- 过期时间
     */
    Map<String, Long> cache_expire = new ConcurrentHashMap<>();

    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 3600;//1小时

    /**  不设置过期时长 */
    public final static int NOT_EXPIRE = -1;

	/**
	 * 设置
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void set(String key, Object value, int expire){
    	if(key == null || value == null) {
			return ;
		}
    	if(open) {
			String jsonString = JSON.toJSONString(value);

			Jedis jedis = jedisPool.getResource();
			try {
				jedis.set(key,jsonString);
				if(expire > NOT_EXPIRE){
					jedis.expire(key,expire);
				}
			}catch (Exception e){
				e.getStackTrace();
			}finally {
				jedis.close();
			}
    	}else{
			cache.put(key, value);
			if(expire > NOT_EXPIRE){
				cache_expire.put(key, System.currentTimeMillis() + expire  * 1000);
			}else{
				cache_expire.put(key, System.currentTimeMillis() + DEFAULT_EXPIRE * 1000);
			}
		}
    }

	/**
	 * 设置
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value){
		set(key, value, NOT_EXPIRE);
    }

	/**
	 * 获取
	 * @param key
	 * @param clazz
	 * @param expire
	 * @param <T>
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz, int expire) {

		if(open) {
			String value = "";
			Jedis jedis = jedisPool.getResource();
			try {
				if(expire > NOT_EXPIRE){
					jedis.expire(key, expire);
				}
				value = jedis.get(key);
			}catch (Exception e){
				e.getStackTrace();
			}finally {
				jedis.close();
			}
			return JSON.parseObject(value,clazz);
    	}else{
			clearExpire();
			return (T)cache.get(key);
		}
    }

	/**
	 * 查看过期时间
	 */
	public String ttl(String key){
		if(open) {
			String value = "";
			Jedis jedis = jedisPool.getResource();
			try {
				value = jedis.ttl(key).toString();
			}catch (Exception e){
				e.getStackTrace();
			}finally {
				jedis.close();
			}
			return value;
		}else{
			long ct = System.currentTimeMillis();
			Long aLong = cache_expire.get(key);
			if(aLong>ct){
				return String.valueOf((aLong-ct)/1000);
			}else{
				return "0";
			}
		}
	}

	/**
	 * 获取
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz) {
		return get(key, clazz, NOT_EXPIRE);
    }


	/**
	 * 查看key是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
    	if(open) {
			boolean exist = false;
			Jedis jedis = jedisPool.getResource();
			try {
				exist = jedis.exists(key);
			}catch (Exception e){
				e.getStackTrace();
			}finally {
				jedis.close();
			}
			return exist;
    	}else{
			clearExpire();
			return cache.containsKey(key);
		}
    }

    public String get(String key) {
    	return get(key , String.class, NOT_EXPIRE);
    }

    public void del(String key) {
    	if(open) {
			Jedis jedis = jedisPool.getResource();
			try {
				jedis.del(key);
			}catch (Exception e){
				e.getStackTrace();
			}finally {
				jedis.close();
			}
    	}else{
			cache.remove(key);
			cache_expire.remove(key);
		}
    }
    
    public Set<String> keys(String pattern){
    	if(open) {
			Set<String> keys = new HashSet<>();
			Jedis jedis = jedisPool.getResource();
			try {
				keys = jedis.keys(pattern);
			}catch (Exception e){
				e.getStackTrace();
			}finally {
				jedis.close();
			}
			return keys;
    	}else{
			clearExpire();
			Set<String> keys = new HashSet<>();
			for(String key : cache.keySet()) {
				if(key.matches(pattern)) {
					keys.add(key);
				}
			}
			return keys;
		}
    }
    
    public void del(Collection<String> keys) {
    	if(open) {
			Jedis jedis = jedisPool.getResource();
			try {
				jedis.del(keys.toArray(new String[keys.size()]));
			}catch (Exception e){
				e.getStackTrace();
			}finally {
				jedis.close();
			}
    	}else{
			clearExpire();
			for(String key : keys) {
				cache.remove(key);
				cache_expire.remove(key);
			}
		}
    }
    
    public synchronized void clearExpire() {
     	
    	long ct = System.currentTimeMillis();

		Set<String> keys = cache_expire.keySet();
    	for(String key : keys) {
    		Long time = cache_expire.get(key);
    		if(time != null && time > 0 && time < ct) {
    			cache.remove(key);
    			cache_expire.remove(key);
    		}
    	}
    }
}
