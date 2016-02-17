package cn.com.fri.spring.security.customFilters;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 路径过滤匹配权限
 * 
 * @author WYQ
 * 
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	private static final Logger log = LoggerFactory
			.getLogger(MyAccessDecisionManager.class);

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		log.info("访问的URL是 ::" + object.toString());
		if (configAttributes == null) {
			return;
		}
		// 所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			// 访问所请求资源所需要的权限
			String needPermission = configAttribute.getAttribute();
			log.info("该资源需要的权限：" + needPermission);
			// 用户所拥有的权限authentication
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				log.info("当前访问者权限有::" + ga.getAuthority());
				if (needPermission.equals(ga.getAuthority())) {
					log.info("您访问权限符合资源所需权限......进入>>>>>>>>");
					return;
				}
			}
		}
		// 没有权限
		throw new AccessDeniedException(" 没有权限访问！ ");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
