package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.Item;

public interface ItemMapper extends SysMapper<Item>{

	//ctrl+shift+y小写,+x大写
	/**
	 * 说明:因为不同的数据库对于大小写有不同的要求,
	 * 操作系统中,Windows不区分大小写,
	 * 但是Linux系统中严格区分大小写,
	 * 因此统一保持小写
	 * @return                              
	/** 查询总记录数*/
	@Select("select count(*) from tb_item")
	int findItemCount();

	/**分页查询记录数
	 *mybatis的mapper接口不允许多值传输,
	 *思路:
	 *将多值封装为单值 
	 *1.将多值封装为对象
	 *2.封装为集合 数组
	 */
	List<Item> findItemByPage(@Param("start")int start, @Param("rows")Integer rows);

	@Select("select name from tb_item_cat where id=#{itemId}")
	String findItemCatNameById(Long itemId);

	void updateStatus(@Param("ids")String[] ids, @Param("status")int status);
	
}
