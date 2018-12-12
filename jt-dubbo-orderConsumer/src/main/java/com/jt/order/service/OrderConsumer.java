package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;

//@Service
public class OrderConsumer{
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;

	/**
	 * 1.获取orderId   userId + 时间戳
	 * 2.补全数据    状态信息    时间信息
	 * 3.分别使用通用Mapper实现三张表入库
	 * 
	 * 思考:
	 * 	  该操作通过3次数据库链接,实现入库操作 效率偏低.
	 * 	 insert into order(字段) values(#{数据});
	 *   insert into orderShipping(字段) values(#{数据});
	 *   <c:foreeach  var=orderItem>
	 *   	insert into orderItem(字段) values(#{数据});
	 *   </c:...>
	 *   jdbc.url=jdbc:mysql://localhost:3306/jtdb?useUnicode=true&characterEncoding=utf8
	 *   &autoReconnect=true&allowMultiQueries=true
	 */
	//jt-dubbo-order项目产生order对象.保存到消息队列rabbitmq框架启动,取到order对象,调用saveOrder
	public void saveOrder(Order order) {
		//拼接orderId号
		//String orderId = order.getUserId() + "" + System.currentTimeMillis();
		String orderId=order.getOrderId();
		Date date = new Date();
		
		//入库订单
		order.setOrderId(orderId);
		order.setStatus(1); 
		order.setCreated(date);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功!!!!!");
		
		//获取订单物流信息
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流信息入库成功!!");
		
		//实现订单商品入库
		List<OrderItem> orderItemList = order.getOrderItems();
		for (OrderItem orderItem : orderItemList) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单入库成功!!!");
		//return orderId;
	}
	
}
