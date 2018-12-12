package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@RequestMapping("/{moduleName}")
	public String register(@PathVariable String moduleName) {
		return moduleName;
	}
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户新增失败!");
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		try {
			
			//根据用户名和密码实现校验,token作为凭证
			String token=userService.findUserByUP(user);
			
			//判断token数据是否为null
			if (StringUtils.isEmpty(token)) {
				throw new RuntimeException();
			}
			
			/**cookie生命周期 单位:秒
			 *value>0 生命周期为秒
			 *value=0 立即删除
			 *value=-1关闭会话删除 
			 */
			//token不为nukk,将数据保存到cookie中
			Cookie cookie=new Cookie("JT_TICKET",token);
			cookie.setPath("/");//权限设定
			cookie.setMaxAge(3600*24*7);
			response.addCookie(cookie);
			return SysResult.oK();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//接收异常
		return SysResult.build(201, "用户登录失败!");
	}

	
	/**实现用户登出操作
	 * 1.清空Redis缓存
	 * 2.删除cookie数据
	 * 3.重定向到首页
	 */
	@RequestMapping("/logout")
	public String logOut(HttpServletResponse response,HttpServletRequest request) {
		
		//1.删除Redis缓存  删除key=token
		String token=null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("JT_TICKET")) {
				token=cookie.getValue();
				break;
			}
		}
		jedisCluster.del(token);
		
		//2.删除cookie
		Cookie cookie=new Cookie("JT_TICKET", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		//3.重定向到首页
		return "redirect:/index.html";
		//response.sendRedirect("/index.html");
	}
	
}
