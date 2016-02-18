package cn.com.fri.spring.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * spring上传过滤的路径----！！！该类暂时停用
 * 
 * 用到得JAR包：(commons-fileupload-1.2.1.jar commons-io-1.3.2.jar
 * httpclient-4.2.5.jar)
 * 
 * @author WYQ
 * 
 */
public class MyMultipartResolver extends CommonsMultipartResolver {

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		// 此路径不用springMvc过滤
		if (request.getRequestURI().equals("/wyq/file/upload")) {
			return false;
		} else {
			return super.isMultipart(request);

		}
	}
}
