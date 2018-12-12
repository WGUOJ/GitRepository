package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 格式如：chenchen/1
		其中chenchen是校验的数据
		Type为类型，可选参数1 username、2 phone、3 email
	 * @param param
	 * @param type
	 * @return
	 */
	//实现用户信息的校验
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public MappingJacksonValue findCheckUser
			(@PathVariable String param,@PathVariable Integer type,String callback) {
		
		//校验用户信息是否存在.存在true 不存在false
		boolean flag=userService.findCheckUser(param,type);
		MappingJacksonValue value=new MappingJacksonValue(SysResult.oK(flag));
		value.setJsonpFunction(callback);//回调结果
		return value;
	}
	
	//实现用户新增
	@RequestMapping("/register")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增用户失败!");
	}
	
	//通过前台传递的用户名和密码实现登录操作
	@RequestMapping("/login")
	@ResponseBody
	public SysResult findUserByUP(User user) {
		try {
			String token=userService.findUserByUP(user);
			if (StringUtils.isEmpty(token)) {
				throw new RuntimeException();
			}
			return SysResult.oK(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户登录失败!");
	}
	
	//实现用户根据token查询用户信息,跨域使用JSONP
	//登录后显示用户名
	@RequestMapping("/query/{token}")
	@ResponseBody
	public MappingJacksonValue findUserByToken(@PathVariable String token,String callback) {
		
		//获取Redis集群中的json串
		String userJSON = jedisCluster.get(token);
		
		MappingJacksonValue jacksonValue=null;
		if (StringUtils.isEmpty(userJSON)) {
			jacksonValue=new MappingJacksonValue(SysResult.build(201, "用户数据查询失败!"));
		}else {
			jacksonValue=new MappingJacksonValue(SysResult.oK(userJSON));
		}
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
}
