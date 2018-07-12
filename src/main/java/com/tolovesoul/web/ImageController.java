package com.tolovesoul.web;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.tolovesoul.utils.BaiduShituUtils;
/**
 * 图片上传接口
 * @author yuanyue
 * @Description: 
 * @date 2018年7月11日
 */
@Controller
@RequestMapping("/image")
public class ImageController {
	
	
	
	@PostMapping("/upload")
	public String updateImage(MultipartFile picture,Model model,String type) {
	    // 把图片保存到图片目录下
	    // 保存图片，这个图片有的时候文件名可能会重复，你保存多了会把原来的图片给覆盖掉，这就不太合适了。
	    // 所以为每个文件生成一个新的文件名
	    String picName = UUID.randomUUID().toString();
	    // 截取文件的扩展名(如.jpg)
	    String oriName = picture.getOriginalFilename();
	    String extName = oriName.substring(oriName.lastIndexOf("."));
	    // 保存文件
	    File file = new File("E:\\image\\" + picName + extName);
	    try {
			picture.transferTo(file);
		} catch (Exception e) {
			model.addAttribute("errorInfo", e.getStackTrace().toString());
			return "../errorInfo";
		}

	    String postFile = BaiduShituUtils.postFile(file, "http://image.baidu.com/pcdutu/a_upload?fr=html5&target=pcSearchImage&needJson=true");
    	Map<String,String> map = new Gson().fromJson(postFile, Map.class);
    	String baiduShitu = BaiduShituUtils.baiduShitu(map.get("url"), map.get("querySign"), map.get("simid"));
    	Document doc = Jsoup.parse(baiduShitu);
    	Elements guessInfos = doc.getElementsByClass("guess-info-word-link guess-info-word-highlight");
    	try {
	    	String text = guessInfos.get(0).text();
	    	//String text = "f&a[]t/e()"; 测试正则表达式
	    	text = text.replaceAll("[/%\",<>&();+-\\[\\]{}]", "");
	    	//text = text.replaceAll("[/]", " ");
	    	System.out.println("猜想："+text);
		    return "redirect:/search/"+type+"/"+URLEncoder.encode(text,"utf-8");
    	} catch (Exception e) {
    		model.addAttribute("errorInfo", "没有搜索到和图片关联的内容");
			return "../errorInfo";
		}
	}

}
