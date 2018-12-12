package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.service.HttpClientService;
@Service
public class ItemServiceImpl implements ItemService{
	
	private ObjectMapper objectMapper=new ObjectMapper();

	@Autowired
	private HttpClientService httpClient;
	
	@Override
	public Item findItemById(Long itemId) {
		//restful风格传参
		String url="http://manage.jt.com/web/item/findItemById/"+itemId;
		
		//获取后台返回的json数据
		String result=httpClient.doGet(url);
		Item item=null;
		try {
			//json串转对象
			item=objectMapper.readValue(result, Item.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemDescById/"+itemId;
		//获取后台返回的数据
		String result=httpClient.doGet(url);
		ItemDesc itemDesc=null;
		try {
			//将结果转成对象
			itemDesc=objectMapper.readValue(result, ItemDesc.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}
