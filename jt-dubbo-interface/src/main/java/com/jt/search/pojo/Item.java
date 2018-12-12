package com.jt.search.pojo;

import org.apache.solr.client.solrj.beans.Field;

import com.jt.common.po.BasePojo;
//此类与solr域中的数据对应
public class Item extends BasePojo{

	/**
 	<field name="id" type="long" indexed="true" stored="true" required="true" multiValued="false" /> 
	<field name="title" type="text_ik" indexed="true" stored="true"/>
	<field name="sellPoint" type="text_ik" indexed="true" stored="true"/>
	<field name="price" type="long" indexed="true" stored="true"/>
	<field name="num" type="int" indexed="true" stored="true"/>
	<field name="image"
	 */
	@Field("id")
	private Long id;
	
	@Field("title")
	private String title;

	@Field("sellPoint")
	private String sellPoint;
	
	@Field("price")
	private Long price;
	
	@Field("image")
	private String images;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String[] getImages() {
		return images.split(",");
	}

	public void setImages(String images) {
		this.images = images;
	}

}
