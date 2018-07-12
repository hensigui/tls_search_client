package com.tolovesoul.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 默认控制器
 * @author yuanyue
 * @Description: 
 * @date 2018年7月11日
 */
@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		//return "/ImageUpload";
		return "/index";
	}
	@RequestMapping("/errorInfo")
	public String errorPage() {
		return "/Error";
	}
	
	@RequestMapping(value="/q/{type}/{query}",method= {RequestMethod.GET,RequestMethod.POST})
	public String strSearch(@PathVariable(value="type") String type ,@PathVariable(value="query") String query, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("query", query);
		return "/result";
	}
}
