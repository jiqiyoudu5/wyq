package cn.com.fri.sys.biz;

import java.util.Set;

import cn.com.fri.basic.biz.IBaseHibernateBiz;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;

public interface ISYSUserBiz extends IBaseHibernateBiz<SYSUser> {

	/**
	 * 根据用户名查找该用户Roles
	 * 
	 * DAO中设定了抓取策略(FetchMode.JOIN), 等于动态指定实体类中的fetch = FetchType.MERGE设置
	 * 
	 * @param username
	 * @return
	 */
	public Set<SYSRole> findUserRolesByUsername(String username);

	/**
	 * VIP转换为普通用户并解除VIP角色权限
	 * 
	 * @param roles
	 * 
	 * @param usernames
	 */
	public void removeVipPower(String[] roles, String username);

}
