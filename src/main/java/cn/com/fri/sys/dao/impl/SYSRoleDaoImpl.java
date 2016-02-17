package cn.com.fri.sys.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.fri.basic.dao.impl.BaseHibernateDaoImpl;
import cn.com.fri.sys.dao.ISYSRoleDao;
import cn.com.fri.sys.po.SYSRole;

@Repository("sysRoleDaoImpl")
public class SYSRoleDaoImpl extends BaseHibernateDaoImpl<SYSRole> implements
		ISYSRoleDao {

}
