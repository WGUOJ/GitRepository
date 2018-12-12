package com.jt.manage.service;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;

public interface ItemService {

	//根据id查找叶子类目的名称
	String findItemCatNameById(Long itemId);

	//分页查找item信息
	EasyUIResult findItemByPage(Integer page,Integer rows);

	//新增商品
	void savaItem(Item item,String desc);

	//修改商品
	void updateItem(Item item,String desc);

	//修改状态(上架)
	void updateStatus(String[] ids, int status);

	//根据id删除商品
	void deleteItems(String[] ids);

	//根据id回显商品信息描述
	ItemDesc findItemDescById(Long itemId);

	Item findItemById(Long itemId);
}
