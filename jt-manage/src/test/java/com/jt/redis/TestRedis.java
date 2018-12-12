package com.jt.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**测试Redis*/
public class TestRedis {

	//测试字符串ip:192.168.138.135,oort:6379
	@Test
	public void testString() {
		Jedis jedis=new Jedis("192.168.138.135", 6379);
		jedis.set("aa", "小a");
		System.out.println(jedis.get("aa"));
	}
	
	//测试哈希
	@Test
	public void testHash() {
		Jedis jedis=new Jedis("192.168.138.135", 6379);
		jedis.hset("dog", "id", "1");
		jedis.hset("dog", "name", "土狗");
		jedis.hset("dog", "age", "15");
		System.out.println(jedis.hget("dog", "name"));
		System.out.println(jedis.hgetAll("dog"));
	}
	
	@Test
	public void testList(){
		Jedis jedis=new Jedis("192.168.138.135",6379);
		jedis.lpush("id", "1","2","3");
		System.out.println(jedis.lpop("id"));
		//System.out.println(jedis.rpop("id"));
	}
	
	//redis事务控制
	@Test
	public void testTX() {
		Jedis jedis=new Jedis("192.168.138.135",6379);
		Transaction transaction = jedis.multi();//开始事务
		transaction.set("aa", "aaa");
		transaction.set("bb", "bbb");
		transaction.set("cc", "ccc");
		transaction.exec();//事务提交
		System.out.println("执行成功!");
		//transaction.discard();//事务回滚
		
	}
	
	
	
	
	
	
	
	
}
