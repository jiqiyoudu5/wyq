package cn.com.fri.spring.security.customFilters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author WYQ </p>
 * 
 *         配置参数： </p>
 * 
 *         defaultTargetUrl(登录成功后跳转地址)</p>
 * 
 *         forwardToDestination(跳转方式::true:forward,false:sendRedirect)</p>
 * 
 *         其他说明：</p>
 * 
 *         自定义登录成功后的处理程序
 * 
 *         在这里可以记录登录用户信息、IP。。。
 * 
 */
public class MyAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler, InitializingBean {

	/** 取得日志记录器log */
	protected Logger log = Logger.getLogger(this.getClass().getName());

	/** 跳转地址 */
	private String defaultTargetUrl;

	/** 跳转方式 */
	private boolean forwardToDestination = false;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isEmpty(defaultTargetUrl))
			log.info("You must configure defaultTargetUrl");
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.print("=============>>>>>>>>IP地址::"
				+ this.getIpAddress(request));
		if (this.forwardToDestination) {
			log.info("Login success,Forwarding to " + this.defaultTargetUrl);
			request.getRequestDispatcher(this.defaultTargetUrl).forward(
					request, response);
		} else {
			log.info("Login success,Redirecting to " + this.defaultTargetUrl);
			this.redirectStrategy.sendRedirect(request, response,
					this.defaultTargetUrl);
		}
	}

	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public void setForwardToDestination(boolean forwardToDestination) {
		this.forwardToDestination = forwardToDestination;
	}

}
