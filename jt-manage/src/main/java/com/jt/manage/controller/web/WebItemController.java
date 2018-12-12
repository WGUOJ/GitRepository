package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.manage.service.ItemService;

/*不能用ItemController名字的原因是:spring容器中id一定不能重复,
 * 否则容器将不能正常启动Map<id,Object>*/
@Controller
@RequestMapping("/web/item")
public class WebItemController {

	@Autowired
	private ItemService itemService;
	
	//根据id查询商品信息
	//http://manage.jt.com/web/item/findItemById/
	@RequestMapping("/findItemById/{itemId}")
	@ResponseBody
	public Item findItemByItemId(@PathVariable  Long itemId) {
		return itemService.findItemById(itemId);
	}
	
	//根据id查询商品详情
	@RequestMapping("/findItemDescById/{itemId}")
	@ResponseBody
	public ItemDesc findItemDescById(@PathVariable Long itemId) {
		return itemService.findItemDescById(itemId);
	}
}
