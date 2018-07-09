package com.tolovesoul.service;

/**
 * 调用webservice服务接口
 * @author yuanyue
 * @Description: 
 * @date 2018年7月6日
 */
public interface SearchService {
	/**
	 * 
	 * @param str 查询关键字
	 * @param start 从何处开始
	 * @return
	 */
	public String search(String str, String start);
}
