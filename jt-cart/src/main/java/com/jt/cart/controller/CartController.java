package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.service.CartService;
import com.jt.common.po.Cart;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	private ObjectMapper objectMapper=new ObjectMapper();
	
	/**实现购物车页面呈现http://www.jt.com/cart/show.html*/
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult queryCart(@PathVariable Long userId) {
		try {
			List<Cart> cartList=cartService.findCartByUserId(userId);
			return SysResult.oK(cartList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车数据查询失败!");
	}
	
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartnNum(@PathVariable Long userId,@PathVariable Long itemId,@PathVariable Integer num) {
		try {
			cartService.updateCartNum(userId,itemId,num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品数量修改失败!");
	}
	
	//http://cart.jt.com/cart/save 新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(String cartJSON) {
		try {
			Cart cart = objectMapper.readValue(cartJSON, Cart.class);
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增购物车失败!");
	}
	
	//http://cart.jt.com/cart/delete/{userId}/{itemId}
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(@PathVariable Long userId,@PathVariable Long itemId) {
		try {
			cartService.deleteCart(userId,itemId);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品删除失败!");
	}
	
	
	
	
}
