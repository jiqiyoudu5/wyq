package cn.com.fri.sys.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.fri.basic.biz.impl.BaseHibernateBizImpl;
import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.sys.biz.ISYSAttachedBiz;
import cn.com.fri.sys.po.SYSAttached;

@Service("sysAttachedBiz")
public class SYSAttachedBiz extends BaseHibernateBizImpl<SYSAttached> implements
		ISYSAttachedBiz {

	@Override
	@Autowired
	public void setBaseDao(IBaseHibernateDao<SYSAttached> sysAttachedDaoImpl) {
		super.baseDao = sysAttachedDaoImpl;
	}

}
