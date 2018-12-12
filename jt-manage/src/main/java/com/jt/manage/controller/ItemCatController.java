package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUITree;

@Controller
@RequestMapping("/item")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 树控件读取URL。子节点的加载依赖于父节点的状态。
	 * 当展开一个封闭的节点，如果节点没有加载子节点，
	 * 它将会把节点id的值作为http请求参数并命名为'id'
	 * 通过URL发送到服务器上面检索子节点
	 * 因此直接使用@RequestParam将请求id绑定至方法参数即可
	 * @param parentId
	 * @return
	 */
	//实现商品分类目录的呈现
	@RequestMapping("/cat/list")
	@ResponseBody
	public List<EasyUITree> findItemCatListById(@RequestParam(value="id",defaultValue="0")Long parentId) {
		//查询一级商品分类标题
		//Long parentId=0L;
		return itemCatService.findCacheItemCat(parentId);
	}
}
