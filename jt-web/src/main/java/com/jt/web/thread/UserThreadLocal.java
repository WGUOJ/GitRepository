package com.jt.web.thread;

import com.jt.common.po.User;
//为了取出userId新建的类
public class UserThreadLocal {

	//如果有多种类型可以使用Map代替User
	private static ThreadLocal<User> userThread=new ThreadLocal<>();
	
	public static void set(User user) {
		userThread.set(user);
	}
	public static User get() {
		return userThread.get();
	}
	
	//防止内存泄漏问题
	public static void remove() {
		userThread.remove();
	}
}
