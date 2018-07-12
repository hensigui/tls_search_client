package com.tolovesoul.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.tolovesoul.utils.AnalyzerUtil;
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
    		//尝试使用相关联想
    		String guessWord = smartGuess(doc);//获取最可能的答案
    		if(guessWord==null) {
    			model.addAttribute("errorInfo", "没有搜索到和图片关联的内容");
    			return "../errorInfo";
    		}else {
    			guessWord = guessWord.replaceAll("[/%\",<>&();+-\\[\\]{}]", "");
    	    	System.out.println("猜想："+guessWord);
    		    try {
					return "redirect:/search/"+type+"/"+URLEncoder.encode(guessWord,"utf-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
					model.addAttribute("errorInfo", "url编码错误");
	    			return "../errorInfo";
				}
    		}
    		
		}
	}
	/**
	 * 相关信息联想，获取最可能答案
	 * @param doc
	 * @return
	 */
	public String smartGuess(Document doc) {
		Elements guessElements = doc.getElementsByClass("source-card-topic-title-link");
		Map<String,Integer> termsMap = new HashMap<String,Integer>();
		if(guessElements.size()>0) {
			for(Element guessElement : guessElements) {
				String text = guessElement.text();//获取相关字符串
				AnalyzerUtil.countTerms(text, termsMap);
			}
			
			List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(termsMap.entrySet());
			Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
	            //升序排序
	            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
	                return o2.getValue().compareTo(o1.getValue());
	            }
	        });
			String guessWord = list.get(0).getKey();//得到最可能的答案
			return guessWord;
		}else {
			return null;
		}
	}

}
