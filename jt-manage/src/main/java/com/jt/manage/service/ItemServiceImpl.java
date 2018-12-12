package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;

	//分页查询商品信息
	@Override
	public EasyUIResult findItemByPage(Integer page, Integer rows) {
		//1.查询商品记录总数
		//int total=itemMapper.findItemCount();
		/*可设置查询参数如id=100,,,
		 * Item item=new Item();
		 * item.setId(100L);*/
		int total=itemMapper.selectCount(null);
		//System.out.println(total);
		/**
		 * SELECT * FROM tb_item ORDER BY updated DESC LIMIT 0,10
		 * SELECT * FROM tb_item ORDER BY updated DESC LIMIT 10,10
		 * SELECT * FROM tb_item ORDER BY updated DESC LIMIT 20,10
		 * SELECT * FROM tb_item ORDER BY updated DESC LIMIT (n-1)*rows,rows
		 */
		int start=(page-1)*rows;
		List<Item> itemlist=itemMapper.findItemByPage(start,rows);
		return new EasyUIResult(total,itemlist);
	}

	//查询商品分类信息
	@Override
	public String findItemCatNameById(Long itemId) {
		
		return itemMapper.findItemCatNameById(itemId);
	}

	/**
	 * 问题描述:
	 * 因为item入库时主键自增,只有入库操作执行后
	 * 才能获取id值.但是itemDesc入库时,必须有
	 * 主键才行.并且item中的id值必须和item中的
	 * id值一致,如何解决?
	 * 解决方案:
	 * 对于操作数据库的线程而言,每次入库都可以获取入库后最后一个值
	 * 获取最后一个id值即可
	 * INSERT INTO USER VALUES(NULL,"后羿",30,"男");
		SELECT LAST_INSERT_ID();
	 */
	//保存商品信息
	@Override
	public void savaItem(Item item,String desc) {
		item.setStatus(1);//1表示商品正常
		item.setCreated(new Date());
		item.setUpdated(item .getCreated());
		itemMapper.insert(item);
		//利用通用Mapper实现入库操作
		
		//实现商品描述信息入库操作
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);
	}

	//修改商品信息
	@Override
	public void updateItem(Item item,String desc) {
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		
		//更新商品描述信息
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	//修改商品状态(上下架)
	@Override
	public void updateStatus(String[] ids, int status) {
		itemMapper.updateStatus(ids,status);
		
	}
	

	/**
	 * 一般删除时先删除关联表的数据
	 * 之后再删除主表中的数据
	 * 发展:数据库中的关联关系,会影响程序的执行性能
	 * 所以后期时,数据库中的全部的关联关系,都通过
	 * 业务层代码维护,主键除外.
	 */
	//根据id删除商品信息
	@Override
	public void deleteItems(String[] ids) {
		//同时删除商品信息描述表
		itemDescMapper.deleteByIDS(ids);
		itemMapper.deleteByIDS(ids);
	}

	//修改商品信息时回显商品描述信息
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

	//前台调用后台查询数据
	@Override
	public Item findItemById(Long itemId) {
		
		return itemMapper.selectByPrimaryKey(itemId);
	}
	
	
	
	
	
	
}
