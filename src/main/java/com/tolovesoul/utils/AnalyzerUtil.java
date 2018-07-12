package com.tolovesoul.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * lucene分词工具
 * @author yuanyue
 * @Description: 
 * @date 2018年7月12日
 */
public class AnalyzerUtil {

    /**
     *
     * Description:         查看分词信息
     * @param str        待分词的字符串
     * @param analyzer    分词器
     *
     */
    public static void displayToken(String str,Analyzer analyzer){
        try {
            //将一个字符串创建成Token流
            TokenStream stream  = analyzer.tokenStream("", new StringReader(str));
        	stream.reset();//增量操作前必须reset
            //保存相应词汇
            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
            while(stream.incrementToken()){
                System.out.print("[" + cta + "]");
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 计算词条
     * @param str
     * @param analyzer
     * @param termsMap
     */
    public static void countTerms(String str,Map<String,Integer> termsMap) {
    	Analyzer analyzer = new SmartChineseAnalyzer();
    	try {
            //将一个字符串创建成Token流
            TokenStream stream  = analyzer.tokenStream("", new StringReader(str));
        	stream.reset();//增量操作前必须reset
            //保存相应词汇
            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
            while(stream.incrementToken()){
                if(termsMap.containsKey(cta.toString())) {
                	termsMap.put(cta.toString(), termsMap.get(cta.toString())+1);//已存在词条，词条数加1
                }else {
                	termsMap.put(cta.toString(), 1);//不存在词条，初始化
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	analyzer.close();
		}
    }
    
    public static void main(String[] args) {
        Analyzer aly1 = new SmartChineseAnalyzer();
        
        String str = "hello kim,I am dennisit,我是 中国人,my email is dennisit@163.com, and my QQ is 1325103287";
        
        AnalyzerUtil.displayToken(str, aly1);
    }
}