package com.jt.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Cart;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private HttpClientService httpClient;
	
	private ObjectMapper objectMapper=new ObjectMapper();
	
	//购物车信息呈现
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		//URL	http://cart.jt.com/cart/query/{userId}
		String url="http://cart.jt.com/cart/query/"+userId;
		String resultJSON = httpClient.doGet(url);
		List<Cart> cartList=new ArrayList<>();
		try {
			/**返回值
			 * status: 200  //200 成功，201 没有查到
			 * msg: “OK”  //返回信息消息
			 * data:
			 */
			//将获取的串转化为对象
			SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
			if (sysResult.getStatus()==200) {
				cartList=(List<Cart>) sysResult.getData();
			}else {
				System.out.println("后台查询数据失败201");
				throw new RuntimeException();
			}
		} catch (Exception e) {
			System.out.println("后台回传数据,前台解析报错:"+e.getMessage());
		}
		return cartList;
	}

	//更新购物车商品数量
	@Override
	public void updateCartNum(Long userId, Long itemId, Integer num) {
		//http://cart.jt.com/cart/update/num/{userId}/{itemId}/{num}
		String url=
				"http://cart.jt.com/cart/update/num/"+userId+"/"+itemId+"/"+num;
		String resultJSON = httpClient.doGet(url);
	}

	@Override
	public void saveCart(Cart cart) {
		//http://cart.jt.com/cart/save	
		String url="http://cart.jt.com/cart/save";
		//简化参数传递,将cart转化为json
		String cartJSON=null;
		try {
			cartJSON = objectMapper.writeValueAsString(cart);
		} catch (Exception e) {
			System.out.println("cart对象转json异常"+e.getMessage());
			e.printStackTrace();
		}
		Map<String, String> params=new HashMap<>();
		params.put("cartJSON", cartJSON);
		httpClient.doPost(url,params);
		
	}

	@Override
	public void deleteCart(Long userId, Long itemId) {
		//http://cart.jt.com/cart/delete/{userId}/{itemId}
		String url="http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		httpClient.doGet(url);
	}

}
