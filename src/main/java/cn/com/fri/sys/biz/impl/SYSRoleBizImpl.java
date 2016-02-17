package cn.com.fri.sys.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.fri.basic.biz.impl.BaseHibernateBizImpl;
import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.sys.biz.ISYSRoleBiz;
import cn.com.fri.sys.dao.ISYSMenuDao;
import cn.com.fri.sys.dao.ISYSResourceDao;
import cn.com.fri.sys.dao.ISYSUserDao;
import cn.com.fri.sys.po.SYSMenu;
import cn.com.fri.sys.po.SYSResource;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;

@Service("sysRoleBizImpl")
public class SYSRoleBizImpl extends BaseHibernateBizImpl<SYSRole> implements
		ISYSRoleBiz {

	@Autowired
	public void setBaseDao(IBaseHibernateDao<SYSRole> sysRoleDaoImpl) {
		super.baseDao = sysRoleDaoImpl;
	}

	@Autowired
	private ISYSResourceDao resDao;

	@Autowired
	private ISYSUserDao userDao;

	@Autowired
	private ISYSMenuDao menuDao;

	@Override
	@Transactional
	public void configRoleResources(String roleId, String[] resIds) {
		SYSRole role = findById(roleId);
		for (String resId : resIds) {
			SYSResource res = resDao.findById(resId);
			role.getResources().add(res);
		}
	}

	@Override
	@Transactional
	public void removeRoleResources(String roleId, String[] resIds) {
		SYSRole role = findById(roleId);
		for (String resId : resIds) {
			SYSResource res = resDao.findById(resId);
			role.getResources().remove(res);
		}
	}

	@Override
	@Transactional
	public void configRoleUsers(String roleId, String[] userIds) {
		SYSRole role = findById(roleId);
		for (String userId : userIds) {
			SYSUser user = userDao.findById(userId);
			role.getUsers().add(user);
		}
	}

	@Override
	@Transactional
	public void removeRoleUsers(String roleId, String[] userIds) {
		SYSRole role = findById(roleId);
		for (String userId : userIds) {
			SYSUser user = userDao.findById(userId);
			role.getUsers().remove(user);
		}
	}

	@Override
	@Transactional
	public void configRoleMenus(String roleId, String[] menuIds) {
		SYSRole role = this.findById(roleId);
		role.getMenus().clear();
		if (menuIds != null && menuIds.length > 0) {
			List<SYSMenu> menuList = menuDao.findIn("id", menuIds);
			role.getMenus().addAll(menuList);
		}
	}

}
