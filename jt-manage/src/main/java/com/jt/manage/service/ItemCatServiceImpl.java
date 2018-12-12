package com.jt.manage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.ItemCat;
import com.jt.common.service.RedisService;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	//private RedisService redisService;
	//private Jedis jedis;//单个连接会造成阻塞
	
	private ObjectMapper objectMapper=new ObjectMapper();

	@Override
	public List<EasyUITree> findItemCatListById(Long parentId) {
		//根据父级id查询商品分类信息
		ItemCat itemCat=new ItemCat();
		itemCat.setParentId(parentId);
		List<ItemCat> itemCatList=itemCatMapper.select(itemCat);
		List<EasyUITree> treeList=new ArrayList<>();
		//遍历结果集,将结果转换为EasyUITree
		for (ItemCat itemCatTemp : itemCatList) {
			EasyUITree easyUITree=new EasyUITree();
			easyUITree.setId(itemCatTemp.getId());
			easyUITree.setText(itemCatTemp.getName());
			//判断是否父级元素,是则关闭,否则打开(最后一个元素不能处于关闭状态)
			String state=itemCatTemp.getIsParent()?"closed":"open";
			easyUITree.setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}

	@Override
	public List<EasyUITree> findCacheItemCat(Long parentId) {
		List<EasyUITree> treeList=new ArrayList<>();
		//用户查询时先查询缓存
		String key="ITEM_CAT_"+parentId;
		System.out.println(key);
		String json = jedisCluster.get(key);
		try {
			if (StringUtils.isEmpty(json)) {
				//缓存中没有数据
				treeList=findItemCatListById(parentId);
				//将数据存到缓存中
				String listJson = objectMapper.writeValueAsString(treeList);
				//将数据保存到Redis中
				jedisCluster.set(key, listJson);
				System.out.println("第一次查询数据库");
			}else {
				//缓存中有数据,直接转化为对象
				treeList=objectMapper.readValue(json, treeList.getClass());
				System.out.println("缓存入库成功");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return treeList;
	}
}
