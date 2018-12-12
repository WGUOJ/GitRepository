package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {
	
	//通过哨兵连接Redis
	@Autowired(required=false)
	private JedisSentinelPool sentinelPool;
	
	public String get(String key) {
		Jedis jedis = sentinelPool.getResource();
		String result=jedis.get(key);
		sentinelPool.returnResource(jedis);
		return result;
	}
	
	//为数据设定超时时间
	public void set(String key,String value,int seconds) {
		Jedis jedis = sentinelPool.getResource();
		jedis.setex(key, seconds, value);
	}
	
	//不设定超时时间
	public void set(String key,String value) {
		Jedis jedis = sentinelPool.getResource();
		jedis.set(key,value);
	}
	
	
	
	
	

	//有的工程需要，有的工程不需要。设置required=false，有就注入，没有就不注入。
   /* @Autowired(required = false)
    private ShardedJedisPool jedisPool;

    public String get(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		String result=jedis.get(key);
		jedisPool.returnResource(jedis);
		return result;
	}
    
    public void set(String key,String value) {
		ShardedJedis jedis=jedisPool.getResource();
		jedis.set(key, value);
		jedisPool.returnResource(jedis);
	}*/
}
