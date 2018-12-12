package com.jt.redis;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

/**
 * 测试ObjectMAPPER
 */
public class TestObjectMapper {

	//将对象转化为json
	@Test
	public void objectMapper() throws IOException {
		User user=new User();
		user.setId(1);
		user.setName("tom");
		user.setAge(12);
		ObjectMapper mapper=new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		System.out.println(json);
		//将json转化为对象
		User u = mapper.readValue(json, User.class);
		System.out.println(u);
	}
}
