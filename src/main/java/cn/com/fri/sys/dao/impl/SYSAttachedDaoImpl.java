package cn.com.fri.sys.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.fri.basic.dao.impl.BaseHibernateDaoImpl;
import cn.com.fri.sys.dao.ISYSAttachedDao;
import cn.com.fri.sys.po.SYSAttached;

@Repository("sysAttachedDaoImpl")
public class SYSAttachedDaoImpl extends BaseHibernateDaoImpl<SYSAttached>
		implements ISYSAttachedDao {

}
