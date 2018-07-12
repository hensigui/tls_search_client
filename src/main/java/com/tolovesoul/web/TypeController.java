package com.tolovesoul.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cx")
public class TypeController {
	/**
	 * type-cx映射表
	 */
	@Resource(name="typeCxMap")
	private Map<String,String> typeCxMap;
	@Value("${update.password}")
	private String password;
	
	/**
	 * 更新页面
	 * @return
	 */
	@RequestMapping("/")
	public String updatePage() {
		return "/updateType";
	}
	
	/**
	 * 更新操作
	 * @param cx
	 * @param token
	 * @param password2
	 * @return
	 */
	@PostMapping("/update")
	@ResponseBody
	public String updateCxToken(String type,String cx,String password2) {
		if(password.equals(password2)) {
			typeCxMap.put(type, cx);
			return "更新成功";
		}else {
			return "更新失败";
		}
	}
	
	/**
	 * 显示当前所有cx-token映射表
	 * @param password2
	 * @return
	 */
	@GetMapping("/show/{password2}")
	@ResponseBody
	public String showCxTokenList(@PathVariable(value="password2") String password2) {
		return typeCxMap.toString();
	}
}
