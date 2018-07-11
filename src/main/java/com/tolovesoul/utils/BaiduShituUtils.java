package com.tolovesoul.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;


/**
 * 百度识图api工具
 * @author yuanyue
 * @Description: 
 * @date 2018年7月3日
 */
public class BaiduShituUtils {
	public static final Gson gson = new Gson();
	
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    /**
     * 上传图片给百度分析，生成参数
     * @param filePath
     * @param requestURL
     * @return
     */
    public static String postFile(String filePath, String requestURL) {
        int res=0;
        String result = null;
        //String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String BOUNDARY = "----WebKitFormBoundary28OlWxWpSqLhQNyx";//暂时固定边界值
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        File file = new File(filePath);
        try {

            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setChunkedStreamingMode(1024 * 1024);// 1024K  
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+ BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空时执行上传
                 */
                OutputStream outputSteam=conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);

                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名
                 */

                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: image/jpeg");
                sb.append(LINE_END);
                sb.append(LINE_END);
                
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);

                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();

                res = conn.getResponseCode();

                if (res == 200) {
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                } else {
                	System.out.println("状态码不正常");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 上传图片给百度分析，生成参数
     * @param filePath
     * @param requestURL
     * @return
     */
    public static String postFile(File file, String requestURL) {
        int res=0;
        String result = null;
        //String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String BOUNDARY = "----WebKitFormBoundary28OlWxWpSqLhQNyx";//暂时固定边界值
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        try {

            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setChunkedStreamingMode(1024 * 1024);// 1024K  
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+ BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空时执行上传
                 */
                OutputStream outputSteam=conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);

                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名
                 */

                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: image/jpeg");
                sb.append(LINE_END);
                sb.append(LINE_END);
                
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);

                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();

                res = conn.getResponseCode();

                if (res == 200) {
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                } else {
                	System.out.println("状态码不正常");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 调用百度接口获取猜想结果，参数由postfile方法生成
     * @param imageUrl
     * @param querySign
     * @param simid
     * @return
     */
    public static String baiduShitu(String imageUrl,String querySign,String simid){
        String uriAPI = null;
        try {
            uriAPI = "http://image.baidu.com/pcdutu?queryImageUrl="+URLEncoder.encode(imageUrl,"utf-8")+"&querySign="+URLEncoder.encode(querySign,"utf-8")+"&fm=home&uptype=upload_pc&result=result_camera&vs=24ef640a0548bdceec8ea6d35f0c25108b52dd4e";
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        HttpGet httpRequest = new HttpGet(uriAPI);
        String strResult = null;
        try {

            HttpResponse httpResponse = new DefaultHttpClient()
                    .execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {

                strResult = EntityUtils.toString(httpResponse
                        .getEntity());
                //System.out.println(strResult);
                return strResult;
            } else {
                strResult = "Error Response";
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResult;
    }
    
    

    
    public static void main(String[] args) {
    	String postFile = postFile("E:\\fsb.jpg", "http://image.baidu.com/pcdutu/a_upload?fr=html5&target=pcSearchImage&needJson=true");
    	Map<String,String> map = gson.fromJson(postFile, Map.class);
    	String baiduShitu = baiduShitu(map.get("url"), map.get("querySign"), map.get("simid"));
    	Document doc = Jsoup.parse(baiduShitu);
    	Elements guessInfos = doc.getElementsByClass("guess-info-word-link guess-info-word-highlight");
    	String text = guessInfos.get(0).text();
    	//String text = "f&a[]t/e()"; 测试正则表达式
    	text = text.replaceAll("[/%\",<>&();+-\\[\\]{}]", "");
    	//text = text.replaceAll("[/]", " ");
    	System.out.println("猜想："+text);
    	try {
			FileUtils.writeStringToFile(new File("E:\\searchResult.html"), baiduShitu);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}