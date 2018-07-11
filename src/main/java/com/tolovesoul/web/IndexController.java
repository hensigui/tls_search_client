package com.tolovesoul.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
		return "/ImageUpload";
	}
	@RequestMapping("/errorInfo")
	public String errorPage() {
		return "/Error";
	}
}
