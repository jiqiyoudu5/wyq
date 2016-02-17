package cn.com.fri.sys.biz.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.fri.basic.biz.impl.BaseHibernateBizImpl;
import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.sys.biz.ISYSResourceBiz;
import cn.com.fri.sys.dao.ISYSResourceDao;
import cn.com.fri.sys.po.SYSResource;
import cn.com.fri.sys.po.SYSRole;

@Service("sysResourceBizImpl")
public class SYSResourceBizImpl extends BaseHibernateBizImpl<SYSResource>
		implements ISYSResourceBiz {

	@Autowired
	public void setBaseDao(IBaseHibernateDao<SYSResource> sysResourceDaoImpl) {
		super.baseDao = sysResourceDaoImpl;
	}

	@Autowired
	private ISYSResourceDao dao;

	@Override
	@Transactional
	public List<String> findAllTypes() {
		return dao.getAllTypes();
	}

	@Override
	@Transactional
	public Set<SYSRole> getRolesByResourceId(String resid) {
		return dao.getRoles(resid);
	}

}
