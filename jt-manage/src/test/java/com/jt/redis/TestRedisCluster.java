package com.jt.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**测试Redis集群*/
public class TestRedisCluster {

	/**
	 * 1.创建集群操作对象
	 * 2.将集群操作的节点存入set集合中
	 */
	@Test
	public void testRedisCluster() {
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.138.135", 7000));
		nodes.add(new HostAndPort("192.168.138.135", 7001));
		nodes.add(new HostAndPort("192.168.138.135", 7002));
		nodes.add(new HostAndPort("192.168.138.135", 7003));
		nodes.add(new HostAndPort("192.168.138.135", 7004));
		nodes.add(new HostAndPort("192.168.138.135", 7005));
		nodes.add(new HostAndPort("192.168.138.135", 7006));
		nodes.add(new HostAndPort("192.168.138.135", 7007));
		nodes.add(new HostAndPort("192.168.138.135", 7008));
		JedisCluster jedisCluster=new JedisCluster(nodes);
		jedisCluster.set("gg", "集群测试成功!!!");
		System.out.println(jedisCluster.get("gg"));
	}
}
