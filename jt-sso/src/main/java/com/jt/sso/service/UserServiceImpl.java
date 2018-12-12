package com.jt.sso.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.sso.mapper.UserMapper;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	private ObjectMapper objectMapper=new ObjectMapper();

	/**
	 * 业务分析: 根据传递的类型type 1.查询username 2.查询phone 3.查询email 校验时,只查询数据的记录总数.
	 * 如果总数=0,证明后台数据没有记录,可以使用 返回false 如果总数>0,证明后台数据有记录,不能使用 返回true
	 */
	@Override
	public boolean findCheckUser(String param, Integer type) {
		// 定义对象
		User user = new User();

		// 1.验证类型
		switch (type) {
		case 1:
			user.setUsername(param);break;
		case 2:
			user.setPhone(param);break;
		case 3:
			user.setEmail(param);break;
		}
		// 2.调用mapper层方法查询记录总数
		int count = userMapper.selectCount(user);

		// 3.返回结果
		return count == 0 ? false : true;
	}

	@Override
	public void saveUser(User user) {
		//数据补全email
		user.setEmail(user.getPhone());//暂时用phone替代
		user.setCreated(new Date());
		user.setUpdated(user.getUpdated());
		userMapper.insert(user);
		
	}

	@Override
	public String findUserByUP(User user) {
		//根据用户名和密码进行查询
		List<User> userList=userMapper.select(user);
		
		if (userList.size()==0) {
			//用户记录不存在
			return null;
		}
		User userDB=userList.get(0);
		
		String token=
				DigestUtils.md5Hex("JT_TICKET_"+System.currentTimeMillis()+user.getUsername());
		try {
			String userJSON = objectMapper.writeValueAsString(userDB);
			jedisCluster.setex(token, 3600*24*7, userJSON);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}
	
}
