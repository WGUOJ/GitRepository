package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.po.Order;
import com.jt.common.po.OrderItem;
import com.jt.common.po.OrderShipping;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	//提交订单入库
	@Override
	public String saveOrder(Order order) {
		
		//1.实现订单入库
		String orderId=""+order.getUserId()+System.currentTimeMillis();
		System.out.println(orderId);
		Date date=new Date();
		
		order.setOrderId(orderId);
		order.setStatus(1);//表示未付款状态
		order.setCreated(date);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单数据入库成功");
		
		//2.实现订单物流入库
		OrderShipping orderShipping=order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流信息入库成功!");
		
		//3.实现订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单商品信息入库成功!");
		
		return orderId;
	}

	@Override
	public Order findOrderById(String orderId) {
		
		Order order=orderMapper.selectByPrimaryKey(orderId);
		OrderShipping orderShipping=orderShippingMapper.selectByPrimaryKey(orderId);
		
		OrderItem orderItem=new OrderItem();
		orderItem.setOrderId(orderId);
		List<OrderItem> orderItems=orderItemMapper.select(orderItem);
		
		order.setOrderItems(orderItems);
		order.setOrderShipping(orderShipping);
		
		return order;
	}
	
	
	
	
	
}
