package cn.com.fri.spring.security.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import cn.com.fri.sys.po.SYSUser;

/**
 * 重写了SECURITY中 USER
 * 
 * @author WYQ
 * 
 */
public class LoginUser extends User {

	private static final long serialVersionUID = 6390183099582671113L;

	private SYSUser user;

	public LoginUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	public SYSUser getUser() {
		return user;
	}

	public void setUser(SYSUser user) {
		this.user = user;
	}

}
