package com.jt.manage.vo;

public class EasyUITree {

	/**
	 * [{id:1,text:"节点名称",state:"clsed"},{},{}]
	 */
	private Long id;
	private String text;
	private String state;//closed open
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public EasyUITree() {
		//无参构造  如果不加无参,则反射/创建对象时不方便
	}
	
	public EasyUITree(Long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}
	
}
