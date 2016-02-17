package cn.com.fri.spring.security.customFilters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Service;

import cn.com.fri.sys.biz.ISYSResourceBiz;
import cn.com.fri.sys.po.SYSResource;
import cn.com.fri.sys.po.SYSRole;

/**
 * 将资源与权限的对应关系加载到内存中,该类在服务器启动的时候加载。
 * 
 * @author WYQ
 */
@SuppressWarnings("deprecation")
@Service
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	private static final Logger log = LoggerFactory
			.getLogger(MySecurityMetadataSource.class);

	@Autowired
	private ISYSResourceBiz resourceBiz;

	@PostConstruct
	public void init() {
		loadResourceDefine();
	}

	/**
	 * 刷新内存
	 */
	public void refresh() {
		resourceMap = null;
		loadResourceDefine();
	}

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	// 加载所有资源与权限的关系
	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<SYSResource> resources = this.resourceBiz.findAll();
			for (SYSResource resource : resources) {
				log.info("附加权限认证：" + resource.getName());
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				// 以权限名封装为Spring的security Object
				// 注意此处没有直接使用resource.getRoles()方法获取role结合，因为在实体类配置中采用的是Lazy为true，在此处
				// 手动设定了抓取策略(FetchMode.JOIN),等于是动态指定了fetch = FetchType.MERGE
				Set<SYSRole> roles = this.resourceBiz
						.getRolesByResourceId(resource.getId());
				for (SYSRole sysRole : roles) {
					ConfigAttribute configAttribute = new SecurityConfig(
							sysRole.getCode());
					configAttributes.add(configAttribute);
				}
				resourceMap.put(resource.getUrl(), configAttributes);
			}
		}

	}

	// 返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		FilterInvocation filterInvocation = (FilterInvocation) object;
		HttpServletRequest request = filterInvocation.getHttpRequest();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);
			if (requestMatcher.matches(request)) {
				// log.info("该资源是需要权限才能访问");
				requestMatcher = null;
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}