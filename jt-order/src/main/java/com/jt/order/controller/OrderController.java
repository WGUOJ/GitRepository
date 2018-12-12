package com.jt.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Order;
import com.jt.common.vo.SysResult;
import com.jt.order.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	//http://www.jt.com/order/create.html
	@RequestMapping("/create")
	@ResponseBody
	public SysResult saveOrder(String orderJSON) {
		try {
			Order order = objectMapper.readValue(orderJSON, Order.class);
			String orderId=orderService.saveOrder(order);
			if (!StringUtils.isEmpty(orderId)) {
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增订单失败!");
	}
	
	//根据orderId查询订单信息
	//http://order.jt.com/order/query/81425700649826
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order findOrderById(@PathVariable String orderId) {
		return orderService.findOrderById(orderId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
