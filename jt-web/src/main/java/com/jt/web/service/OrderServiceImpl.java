package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Order;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private HttpClientService httpClient;
	
	private ObjectMapper objectMapper=new ObjectMapper();
	
	String orderId=null;
	
	//URL	http://order.jt.com/order/create
	@Override
	public String saveOrder(Order order) {
		String url="http://order.jt.com/order/create";
		try {
			//用json数据传递
			String orderJSON = objectMapper.writeValueAsString(order);
			
			Map<String, String> params=new HashMap<>();
			params.put("orderJSON", orderJSON);
			
			String sysJSON = httpClient.doPost(url, params);
			
			SysResult sysResult=objectMapper.readValue(sysJSON, SysResult.class);
			
			if (sysResult.getStatus()==200) {
				orderId=(String) sysResult.getData(); 
			}
		} catch (Exception e) {
			System.out.println("前台传输数据或者解析数据异常!");
			e.printStackTrace();
			throw new RuntimeException();
		}
		return orderId;
	}

	@Override
	public Order findOrderById(String id) {
		//http://order.jt.com/order/query/81425700649826
		String url="http://order.jt.com/order/query/"+id;
		
		String orderJSON = httpClient.doGet(url);
		Order order=null;
		try {
			order=objectMapper.readValue(orderJSON, Order.class);
			
		} catch (Exception e) {
			System.out.println("获取order对象异常"+e.getMessage());
		}
		
		return order;
	}

}
