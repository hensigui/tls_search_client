package com.tolovesoul.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tolovesoul.service.SearchService;
/**
 * 搜索接口
 * @author yuanyue
 * @Description: 
 * @date 2018年7月11日
 */
@RestController
public class SearchController {
	/**
	 * type-cx映射表
	 */
	@Resource(name="typeCxMap")
	private Map<String,String> typeCxMap;
	
	@Autowired
	private SearchService searchService;
	@GetMapping("/search/{type}/{query}")
	public String testSearch(@PathVariable(value="type") String type ,@PathVariable(value="query") String query) {
		return searchService.search(query, "0",typeCxMap.get(type));
	}
}
