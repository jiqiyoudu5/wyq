package cn.com.fri.sys.dao;

import java.util.Set;

import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;

public interface ISYSUserDao extends IBaseHibernateDao<SYSUser> {

	/**
	 * 使用FetchMode.JOIN来查询数据
	 * 
	 * @param username
	 * @return
	 */
	public Set<SYSRole> getRoles(String username);
}
