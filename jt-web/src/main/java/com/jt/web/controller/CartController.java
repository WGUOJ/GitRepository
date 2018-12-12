package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Cart;
import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/show")
	public String show(Model model) {
		//根据userId查询购物车信息
		User user = UserThreadLocal.get();
		//已经在拦截器中放到线程里面,可以直接取出
		Long userId=user.getId();
		List<Cart> cartList=cartService.findCartByUserId(userId);
		//将cartList数据保存到request对象中
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	//http://www.jt.com/service/cart/update/num/562379/8
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum
			(@PathVariable Long itemId,@PathVariable Integer num) {
		try {
			User user = UserThreadLocal.get();
			Long userId=user.getId();
			cartService.updateCartNum(userId,itemId,num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车商品数量修改失败!");
	}
	
	//http://www.jt.com/cart/add/562379.html
	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable Long itemId,Cart cart) {
		User user = UserThreadLocal.get();
		Long userId=user.getId();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartService.saveCart(cart);
		//重定向到购物车显示页面
		return "redirect:/cart/show.html";
	}
	
	//http://www.jt.com/cart/delete/1369278.html
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId) {
		User user = UserThreadLocal.get();
		Long userId=user.getId();
		cartService.deleteCart(userId,itemId);
		return "redirect:/cart/show.html";
	}
}
