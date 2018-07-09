package com.tolovesoul.interceptor;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 请求消息头拦截器，用于添加身份识别
 * @author yuanyue
 * @Description: 
 * @date 2018年7月6日
 */
@Component
public class AddHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
	@Value("${webservice.userName}")
	private String userName;
	@Value("${webservice.password}")
	private String password;
	
	public AddHeaderInterceptor() {
		super(Phase.PREPARE_SEND); // 准备发送SOAP消息的时候调用拦截器
	}

	public void handleMessage(SoapMessage message) throws Fault {
		List<Header> headerList=message.getHeaders();
		Document doc=DOMUtils.createDocument();
		Element ele=doc.createElement("authHeader");
		Element uElement=doc.createElement("userName");
		uElement.setTextContent(userName);
		Element pElement=doc.createElement("password");
		pElement.setTextContent(password);
		
		ele.appendChild(uElement);
		ele.appendChild(pElement);
		
		headerList.add(new Header(new QName("tolovesoul"),ele));
		
	}
	
	

}
