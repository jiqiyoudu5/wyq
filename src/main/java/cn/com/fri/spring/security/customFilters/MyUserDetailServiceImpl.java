package cn.com.fri.spring.security.customFilters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.com.fri.spring.security.utils.LoginUser;
import cn.com.fri.sys.biz.ISYSUserBiz;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;

/**
 * 用户登录处理
 * 
 * @author WYQ
 * 
 */
public class MyUserDetailServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory
			.getLogger(MyUserDetailServiceImpl.class);

	@Autowired
	private ISYSUserBiz sysUserBiz;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		log.info("用户名:: " + username);
		SYSUser user = this.sysUserBiz.findById(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);

		boolean enables = user.getEnabled();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = !user.getLocked();
		LoginUser userdetail = new LoginUser(user.getUsername(),
				user.getPassword(), enables, accountNonExpired,
				credentialsNonExpired, accountNonLocked, grantedAuths);
		userdetail.setUser(user);
		log.info("当前用户存放入userdetail中!");
		return userdetail;
	}

	// 取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(SYSUser user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		// DAO中设定了抓取策略(FetchMode.JOIN),等于动态指定实体类中的fetch = FetchType.MERGE设置
		Set<SYSRole> roles = sysUserBiz.findUserRolesByUsername(user
				.getUsername());
		String userRoles = "";
		for (SYSRole role : roles) {
			userRoles += role.getCode() + "|";
			authSet.add(new SimpleGrantedAuthority(role.getCode()));
		}
		log.info(user.getUsername() + " 拥有的权限有::" + userRoles);
		return authSet;
	}
}