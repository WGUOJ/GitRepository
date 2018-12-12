package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	//跳转首页
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	/**
	 * index页面跳转时,地址栏时
	 * "attributes:{'url':'/page/item-add'}">新增商品</li>
	 * 因此地址栏需要添加/page
	 * RestFul结构
	 * 格式特点:
	 * 1.在url中将需要提交的参数使用"/"进行分割
	 * localhost:8091/addUser/....
	 * 2.在接收端,将参数用{}包裹并且参数的位置是固定的
	 * 3.变量的名称必须和{}中的名称一致,之后通过@PathVarible注解实现数据的传递.
	 * 
	 * 功能:
	 * 1.实现了通用页面的跳转问题
	 * 2.减少了代码传递的字节数
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		return moduleName;
	}
	
}
