package cn.com.fri.spring.security.customFilters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.Assert;

/**
 * 没有重写UsernamePasswordAuthenticationFilter,
 * 这里直接继承AbstractAuthenticationProcessingFilter类验证登陆
 * 
 * @author WYQ
 * 
 */
public class MyUsernamePasswordAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "wyq_username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "wyq_password";
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";
	private String usernameParameter = "wyq_username";
	private String passwordParameter = "wyq_password";
	private boolean postOnly = true;
	// 获取前台输入的验证码
	private String validateCodeParameter = "validateCode";
	// 是否开启验证码功能
	private boolean openValidateCode = false;

	public MyUsernamePasswordAuthenticationFilter() {
		super("/wyq_login");
	}

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if ((this.postOnly) && (!request.getMethod().equals("POST"))) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: "
							+ request.getMethod());
		}
		String code = obtainValidateCode(request).toUpperCase();
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
				|| StringUtils.isBlank(code)) {
			throw new AuthenticationServiceException("请填写完整登陆信息！");
		}
		// 开启验证码功能
		if (isOpenValidateCode()) {
			this.checkValidateCode(request, code);
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		setDetails(request, authRequest);

		return getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(this.passwordParameter);
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(this.usernameParameter);
	}

	protected void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource
				.buildDetails(request));
	}

	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter,
				"Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter,
				"Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return this.usernameParameter;
	}

	public final String getPasswordParameter() {
		return this.passwordParameter;
	}

	// 匹对验证码的正确性
	/**
	 * @param code
	 *            前台输入的验证码
	 * @param localValidateCode
	 *            后台生成的验证码
	 * @param request
	 * @throws Exception
	 */
	public void checkValidateCode(HttpServletRequest request, String code) {
		String localValidateCode = (String) request.getSession().getAttribute(
				"localValidateCode");
		if (request.getSession(false) != null) // 清理session中验证码
		{
			request.getSession().removeAttribute("localValidateCode");
			// request.getSession(false).invalidate();
		}
		// 验证码判断
		if (StringUtils.isNotBlank(localValidateCode)
				&& !localValidateCode.equalsIgnoreCase(code)) {
			throw new AuthenticationServiceException("验证码输入错误！");
		}
	}

	private String obtainValidateCode(HttpServletRequest request) {
		Object obj = request.getParameter(this.validateCodeParameter);
		return null == obj ? "" : obj.toString().trim();
	}

	public String getValidateCodeParameter() {
		return validateCodeParameter;
	}

	public void setValidateCodeParameter(String validateCodeParameter) {
		this.validateCodeParameter = validateCodeParameter;
	}

	public boolean isOpenValidateCode() {
		return openValidateCode;
	}

	public void setOpenValidateCode(boolean openValidateCode) {
		this.openValidateCode = openValidateCode;
	}
}