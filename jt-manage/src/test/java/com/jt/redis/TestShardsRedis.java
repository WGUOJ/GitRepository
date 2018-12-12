package com.jt.redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class TestShardsRedis {

	@Test
	public void test01() {
		List<JedisShardInfo> shardInfos=new ArrayList<>();
		shardInfos.add(new JedisShardInfo("192.168.138.135",6379));
		shardInfos.add(new JedisShardInfo("192.168.138.135",6380));
		shardInfos.add(new JedisShardInfo("192.168.138.135",6381));
		
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		//最大连接数
		poolConfig.setMaxTotal(500);
		//最大空闲连接数量
		poolConfig.setMaxIdle(20);
		//实现连接池的操作
		ShardedJedisPool pool=
				new ShardedJedisPool(poolConfig, shardInfos);
		/*ShardedJedis shardedJedis=new ShardedJedis(shardInfos);*/
		ShardedJedis shardedJedis = pool.getResource();
		shardedJedis.set("hey", "boy");
		System.out.println(shardedJedis.get("hey"));
		//将连接直接关闭
		//shardedJedis.close();
		//将连接还回池中
		pool.returnResource(shardedJedis);
	}
}
