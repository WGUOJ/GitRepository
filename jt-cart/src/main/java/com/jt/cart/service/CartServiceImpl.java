package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.common.po.Cart;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		return cartMapper.select(cart);
	}

	@Override
	public void updateCartNum(Long userId, Long itemId, Integer num) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cart.setNum(num);
		cart.setUpdated(new Date());
		cartMapper.updateCartNum(cart);
	}

	/**新增购物车
	 *1.跟胡userId和itemId查询购物车信息
	 *if 有数据
	 *	累加并更新购物车记录
	 *else 无数据
	 *	补全数据后新增购物车
	userId 用户ID
	itemId 商品ID
	itemTitle 商品标题
	itemImage 商品主图
	itemPrice 商品价格
	num 商品数量
	*/
	@Override
	public void saveCart(Cart cart) {
		//查询购物车记录
		Cart cartDB=cartMapper.findCartByUI(cart);
		
		if (cartDB==null) {
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {
			int num=cartDB.getNum()+cart.getNum();
			cartDB.setNum(num);
			cartDB.setUpdated(new Date());
			cartMapper.updateByPrimaryKeySelective(cartDB);
		}
	}

	@Override
	public void deleteCart(Long userId, Long itemId) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartMapper.delete(cart);
	}

}
