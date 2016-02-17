package cn.com.fri.sys.dao.impl;

import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.fri.basic.dao.impl.BaseHibernateDaoImpl;
import cn.com.fri.sys.dao.ISYSUserDao;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;

@Repository("sysUserDaoImpl")
public class SYSUserDaoImpl extends BaseHibernateDaoImpl<SYSUser> implements
		ISYSUserDao {

	@Override
	public Set<SYSRole> getRoles(String username) {
		Criteria c = getSession().createCriteria(SYSUser.class);
		c.add(Restrictions.eq("username", username));
		c.setFetchMode("roles", FetchMode.JOIN);
		SYSUser user = (SYSUser) c.uniqueResult();
		return user.getRoles();
	}
}
