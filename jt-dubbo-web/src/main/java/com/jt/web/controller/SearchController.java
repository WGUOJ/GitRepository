package com.jt.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.search.pojo.Item;
import com.jt.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	SearchService searchService;
	
	@RequestMapping("/search")
	public String search(String q,Model model) throws UnsupportedEncodingException {
		/**
		 * 通过地址栏访问search.html?q=手机
		 * 地址栏访问是get请求
		 * get请求中文乱码需要程序处理
		 * post请求中文乱码是由web.xml中的filter处理
		 */
		byte[] data=q.getBytes("ISO-8859-1");
		
		//数据发给dubbo提供者
		//dubbo中消费者发送数据给提供者用的编码是utf-8
		String key=new String(data, "UTF-8");
		
		//通过调用接口的抽象方法调用微服务的提供者
		List<Item> itemList=searchService.findItemByKey(key);
		
		model.addAttribute("itemList", itemList);
		
		//转发到search.jsp
		return "search";
	}
}
