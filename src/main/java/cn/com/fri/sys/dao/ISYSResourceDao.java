package cn.com.fri.sys.dao;

import java.util.List;
import java.util.Set;

import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.sys.po.SYSResource;
import cn.com.fri.sys.po.SYSRole;

public interface ISYSResourceDao extends IBaseHibernateDao<SYSResource> {

	public List<String> getAllTypes();

	/**
	 * 使用FetchMode.JOIN来查询数据
	 * 
	 * @param resid
	 * @return
	 */
	public Set<SYSRole> getRoles(String resid);
}
