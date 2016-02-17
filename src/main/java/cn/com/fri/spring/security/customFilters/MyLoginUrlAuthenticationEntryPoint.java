package cn.com.fri.spring.security.customFilters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * 自定义登录跳转验证： 未登录跳转； session过期跳转
 * 
 * @author WYQ
 * 
 */
@SuppressWarnings("deprecation")
public class MyLoginUrlAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		//判断是否为异步请求
		if (null != request.getHeader("X-Requested-With")
				&& "XMLHttpRequest".equalsIgnoreCase(request
						.getHeader("X-Requested-With"))) {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("sessionstatus", "timeout");
			response.setStatus(300);
		} else {
			//普通请求
			super.commence(request, response, authException);
		}
	}

}
