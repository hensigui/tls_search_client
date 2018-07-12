package com.tolovesoul.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.tolovesoul.interceptor.AddHeaderInterceptor;
/**
 * webservice客户端配置类
 * @author yuanyue
 * @Description: 
 * @date 2018年7月6日
 */
@Configuration
public class TlsClientConfig {
	@Value("${webservice.url}")
	private String webServiceUrl;
	@Autowired
	private AddHeaderInterceptor addHeaderInterceptor;
	
	
	@Bean
	public Client client() {
		// 创建动态客户端
	    JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	    Client client = dcf.createClient(webServiceUrl);
	    // 添加自定义验证
	    client.getOutInterceptors().add(addHeaderInterceptor);
	    return client;
	}
	
    /**
     * 用于存放type-cx映射
     * @return
     */
    @Bean(name="typeCxMap")
    public Map<String,String> typeCxMap(){
    	return new HashMap<String,String>();
    }
}
