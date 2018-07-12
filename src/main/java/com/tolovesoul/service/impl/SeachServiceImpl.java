package com.tolovesoul.service.impl;

import org.apache.cxf.endpoint.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tolovesoul.service.SearchService;
/**
 * 搜索接口实现类
 * @author yuanyue
 * @Description: 
 * @date 2018年7月6日
 */
@Service
public class SeachServiceImpl implements SearchService{
	@Autowired
	private Client client;

	@Override
	public String search(String str, String start,String cx) {
		try {
			Object[] objects = client.invoke("search", str , start , cx);
			return (String) objects[0];
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}

}
