package com.jt.jt_redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class DockerTest {

	//测试单个redis
	@Test
	public void jedis() {
		Jedis jedis =new Jedis("192.168.138.137",7000);
		jedis.set("name", "tony");
		String string=jedis.get("name");
		System.out.println(string);
		jedis.close();
	}
	
	//测试redis分片
	@Test
	public void jedis3() {
		//2.创建分片的连接池
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		poolConfig.setMaxTotal(500);
		poolConfig.setMaxIdle(20);
		
		//3.准备redis的分片
		List<JedisShardInfo> shards =new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("192.168.138.137",7000));
		shards.add(new JedisShardInfo("192.168.138.137",7001));
		shards.add(new JedisShardInfo("192.168.138.137",7002));
		
		//1.创建分片的对象
		ShardedJedisPool jedisPool=new ShardedJedisPool(poolConfig, shards);
		
		//4.获取jedis对象
		ShardedJedis shardedJedis=jedisPool.getResource();
		
		//5.redis的存取值操作
		for (int i = 0; i < 9; i++) {
			shardedJedis.set("n"+i, "我是分片操作"+i);
		}
	}
}
