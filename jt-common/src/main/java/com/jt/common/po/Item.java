package com.jt.common.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name="tb_item")//将对象与表一一映射
//json转对象时(调用set方法)忽略未知属性(没有set方法)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Item extends BasePojo{
	private static final long serialVersionUID = -9219915256526652056L;
	@Id	//定义主键
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//主键自增
	private Long id;			//商品id
	private String title;		//商品标题
	
	//@Column(name="sell_point")//指定属性和字段一一映射
	private String sellPoint;	//商品卖点
	private Long price;			//价格
	private Integer num;		//库存数量
	private String barcode;		//条形码
	private String image;		//图片
	private Long cid;			//所属分类
	private Integer status;		//状态	1正常，2下架，3删除
	
	//为了满足页面调用需求,添加get方法
	public String[] getImages() {
		return image.split(",");
	}
	
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
