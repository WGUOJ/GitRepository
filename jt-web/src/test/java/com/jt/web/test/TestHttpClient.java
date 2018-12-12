package com.jt.web.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {

	@Test
	public void testGet() throws ClientProtocolException, IOException {
		//1.定义请求对象
		CloseableHttpClient httpClient =
				HttpClients.createDefault();//创建默认连接
		
		//2.定义请求网址
		String url="https://www.baidu.com";
		
		//3.定义请求对象
		HttpGet get=new HttpGet(url);
		
		//4.发起请求
		CloseableHttpResponse response = httpClient.execute(get);
		
		//5.判断请求是否正确(状态码200)
		if (response.getStatusLine().getStatusCode()==200) {
			
			//6.获取返回值结果
			String result=EntityUtils.toString(response.getEntity());
			System.out.println("返回值结果:"+result);
		}		
	}
	@Test
	public void testPost() throws ClientProtocolException, IOException {
		//1.定义请求对象
		CloseableHttpClient httpClient =
				HttpClients.createDefault();//创建默认连接
		
		//2.定义请求网址
		String url="https://www.baidu.com";
		
		//3.定义请求对象
		HttpPost post=new HttpPost(url);
		
		//4.发起请求
		CloseableHttpResponse response = httpClient.execute(post);
		
		//5.判断请求是否正确(状态码200)
		if (response.getStatusLine().getStatusCode()==200) {
			
			//6.获取返回值结果
			String result=EntityUtils.toString(response.getEntity());
			System.out.println("返回值结果:"+result);
		}		
	}
	
	
	
	
}
