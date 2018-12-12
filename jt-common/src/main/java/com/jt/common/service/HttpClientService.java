package com.jt.common.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired(required=false)
    private CloseableHttpClient httpClient;

    @Autowired(required=false)
    private RequestConfig requestConfig;

    /**
     * 工具类开发步骤:
     * 需求:
     * 	根据URL发起请求,最终获取响应结果
     * 设计:
     * 	参数设计:
     * 		1.String类型 URL 用户发起请求地址
     * 		2.Map数据类型 map<String,String>
     * 		3.String charset 设定字符集编码
     * GET请求参数传递规则:
     * 	用户传参id=1 name=tom age=12
     * 	get传参:url?id=1&name=tom....
     */
    
    public String doGet(String url,Map<String,String> params,String charset) {
		
    	String result=null;
    	//1.判断字符集编码是否为空,如果为空则给定默认值utf-8
    	if (StringUtils.isEmpty(charset)) {
			charset="UTF-8";
		}
    	
    	//2.判断用户是否需要传递参数
    	if (params!=null) {
			try {
				URIBuilder uriBuilder=new URIBuilder(url);
				for (Map.Entry<String, String> entry : params.entrySet()) {
					uriBuilder.addParameter(entry.getKey(),entry.getValue());
				}
				url=uriBuilder.build().toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	//3.定义参数提交对象
    	HttpGet get=new HttpGet(url);

    	//4.为请求设定超时时间
    	get.setConfig(requestConfig);
    	
    	//5.通过httpClient发送请求
    	try {
    		CloseableHttpResponse response = httpClient.execute(get);
    		if (response.getStatusLine().getStatusCode()==200) {
				//表示程序调用成功
    			result=EntityUtils.toString(response.getEntity(),charset);
			}else {
				System.out.println("调用异常:状态信息:"+response.getStatusLine().getStatusCode());
				throw new RuntimeException();
			}
    		
		} catch (Exception e) {
			e.printStackTrace();
			//异常信息处理
			//后期维护...
		}
    	return  result;
	}
    
    //排除用户输入参数缺省
    public String doGet(String url) {
		return doGet(url, null, null);
	}
    
    public String doGet(String url,Map<String, String> params) {
		return doGet(url, params, null);
	}
    
    public String doGet(String url,String charset) {
		return doGet(url, null, charset);
	}
    
    //实现httpClient Post提交
    public String doPost(String url,Map<String, String> params,String charset) {
		
    	String result=null;
    	
    	//1.定义请求类型
    	HttpPost post=new HttpPost(url);
    	//设置超时时间
    	post.setConfig(requestConfig);
    	
    	//2.判断字符集是否为空
    	if (StringUtils.isEmpty(charset)) {
			charset="UTF-8";
		}
    	
    	//3.判断用户是否传递了参数
    	if (params!=null) {
    		//3.2准备list集合
    		List<NameValuePair> parameters=new ArrayList<>();
    		
    		//3.2将数据封装到list集合中
    		for (Map.Entry<String, String> entry : params.entrySet()) {
				parameters.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
			}
    		
			//3.1模拟表单提交,采用utf-8编码
    		try {
				UrlEncodedFormEntity formEntity=new UrlEncodedFormEntity(parameters,charset);
				
				//3.4将实体对象封装到请求对象中
				post.setEntity(formEntity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
    	
    	//4.发送请求
    	try {
			CloseableHttpResponse response = httpClient.execute(post);
			//4.1判断返回值状态
			if (response.getStatusLine().getStatusCode()==200) {
				//4.2表示访问成功
				result=EntityUtils.toString(response.getEntity(),charset);
			}else {
				System.out.println("获取状态码信息:"+response.getStatusLine().getStatusCode());
				throw new RuntimeException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return result;
	}
    
    public String doPost(String url) {
		return doPost(url,null,null);
	}
    
    public String doPost(String url,Map<String, String> params) {
		return doPost(url, params,null);
	}
    
    public String doPost(String url,String charset) {
		return doPost(url,null,charset);
	}
    
}
    
    
   
