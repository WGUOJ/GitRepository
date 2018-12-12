package com.jt.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

//定义用户拦截器
public class UserInterceptor implements HandlerInterceptor{

	@Autowired
	private JedisCluster jedisCluster;
	
	private ObjectMapper objectMapper=new ObjectMapper();
	/**
	 * 1.获取用户cookie拿到token数据
	 * 2.判断token是否有数据
	 * 		false: 表示用户未登录  重定向到用户登录页面
	 * 		true: 表示用户之前登录过(现在不一定处于登录状态) 
	 * 			从Redis中根据token获取userJSON,再次判断是否有数据
	 * 				false: 则重定向到登录页面
	 * 				true: 表示有数据,则予以放行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取cookie中的token
		String token=null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("JT_TICKET".equals(cookie.getName())) {
				token=cookie.getValue();
				break;
			}
		}
		//2.判断token是否有数据
		if (token!=null) {
			//2.1判断Redis集群中是否有数据
			String userJSON = jedisCluster.get(token);
			if (userJSON!=null) {//表示用户已经登录,放行
				User user = objectMapper.readValue(userJSON, User.class);
				//将user数据保存到threadLocal中
				UserThreadLocal.set(user);
				return true;
			}
		}
		//重定向到登录页面
		response.sendRedirect("/user/login.html");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//关闭thredLocal
		UserThreadLocal.remove();
	}

}
