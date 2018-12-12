package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
	
	//根据商品分类的id查询名称
	@RequestMapping(value="/cat/queryItemName",produces="text/html;charset=utf-8")
	@ResponseBody
	public String findItemCatNameById(Long itemId) {
		return itemService.findItemCatNameById(itemId);
	}
	
	//实现商品的新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc) {
		try {
			itemService.savaItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品新增失败!");
	}
	
	//实现商品信息的修改
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc) {
		try {
			itemService.updateItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品更新失败!");
	}
	
	/**
	 * springmvc中参数接收
	 * 1.接收简单类型int String...
	 * 2.使用对象接收 pojo/
	 * 3.使用集合数据接收参数数组 [] list
	 * 4.为对象的引用赋值
	 * 		页面:name="id" value="100"
	 * 		对象:User{id,name,age}/Dog{id,type}
	 * 		接收:
	 * @param ids
	 * @return
	 */
	//实现商品的上架
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(String[] ids) {
		try {
			int status=1;//表示正常
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "上架失败!");
	}
	
	//实现商品的下架
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(String[] ids) {
		try {
			int status=2;//表示下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "下架失败!");
	}
	
	//实现商品的删除
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItems(String[] ids) {
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品删除失败!");
	}
	
	//根据商品id查询商品详情信息
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findDescById(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc=itemService.findItemDescById(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品信息查询失败!");
	}
	
	
	
	
}
