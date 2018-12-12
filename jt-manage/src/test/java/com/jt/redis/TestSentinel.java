package com.jt.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**测试哨兵*/
public class TestSentinel {

	@Test
	public void test01() {
		/**
		 * masterName:主机的变量名称
		 * sentinels:表示哨兵的连接
		 */
		Set<String> sentinels=new HashSet<>();
		/*sentinels.add(new HostAndPort("192.168.138.135", 26379).toString());*/
		sentinels.add("192.168.138.135:26379");
		JedisSentinelPool sentinelPool=
				new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = sentinelPool.getResource();
		jedis.set("hello", "哨兵");
		System.out.println(jedis.get("hello"));
	}
}
