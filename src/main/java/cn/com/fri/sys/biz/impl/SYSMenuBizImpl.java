package cn.com.fri.sys.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.fri.basic.biz.impl.BaseHibernateBizImpl;
import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.sys.biz.ISYSMenuBiz;
import cn.com.fri.sys.biz.ISYSRoleBiz;
import cn.com.fri.sys.biz.ISYSUserBiz;
import cn.com.fri.sys.dao.ISYSMenuDao;
import cn.com.fri.sys.po.SYSMenu;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;
import cn.com.fri.sys.utils.MenuTree;

@Service("sysMenuBizImpl")
public class SYSMenuBizImpl extends BaseHibernateBizImpl<SYSMenu> implements
		ISYSMenuBiz {

	@Autowired
	public void setBaseDao(IBaseHibernateDao<SYSMenu> sysMenuDaoImpl) {
		super.baseDao = sysMenuDaoImpl;
	}

	@Autowired
	private ISYSMenuDao sysMenuDao;

	@Autowired
	private ISYSRoleBiz roleBiz;

	@Autowired
	private ISYSUserBiz userBiz;

	@Override
	@Transactional
	public MenuTree findMenuTree() throws IllegalAccessException,
			InvocationTargetException {
		SYSMenu root = sysMenuDao.findById("402883e04332f56e014332f5716d0001");
		if (root != null) {
			MenuTree tree = new MenuTree();
			BeanUtils.copyProperties(root, tree);
			tree = findChildMenu(tree);
			return tree;
		}
		return null;
	}

	@Override
	@Transactional
	public MenuTree findMenuTreeByUser(String userLine)
			throws IllegalAccessException, InvocationTargetException {
		SYSMenu root = sysMenuDao.findById("402883e043330ab50143330ab8920001");
		if (root != null) {
			SYSUser user = userBiz.findById(userLine);
			Set<SYSRole> roles = user.getRoles();
			Set<SYSMenu> menus = new HashSet<SYSMenu>();
			for (SYSRole role : roles) {
				menus.addAll(role.getMenus());
			}
			MenuTree tree = new MenuTree();
			BeanUtils.copyProperties(root, tree);
			return buildTree(tree, menus);
		}
		return null;
	}

	@Override
	@Transactional
	public Set<SYSMenu> findMenuByRoleId(String roleId) {
		SYSRole role = roleBiz.findById(roleId);
		Set<SYSMenu> menus = role.getMenus();
		Set<SYSMenu> sm = new HashSet<SYSMenu>();
		for (SYSMenu m : menus) {
			sm.add(m);
		}
		return sm;
	}

	private MenuTree buildTree(MenuTree tree, Set<SYSMenu> menus)
			throws IllegalAccessException, InvocationTargetException {
		for (SYSMenu menu : menus) {
			if (menu.getPid().equals(tree.getId())) {
				MenuTree mt = new MenuTree();
				BeanUtils.copyProperties(menu, mt);
				tree.getChildren().add(mt);
			}
		}
		for (MenuTree menuTree : tree.getChildren()) {
			menuTree = this.buildTree(menuTree, menus);
		}
		return tree;
	}

	private MenuTree findChildMenu(MenuTree tree)
			throws IllegalAccessException, InvocationTargetException {
		List<SYSMenu> menus = sysMenuDao.findByProperty("pid", tree.getId());
		if (menus == null || menus.size() == 0) {
			return tree;
		}
		for (SYSMenu sysMenu : menus) {
			MenuTree d = new MenuTree();
			BeanUtils.copyProperties(sysMenu, d);
			d = this.findChildMenu(d);
			tree.getChildren().add(d);
		}
		return tree;

	}

	@Override
	public Set<SYSMenu> findMenusByUser(String userLine) {
		SYSUser user = userBiz.findById(userLine);
		Set<SYSRole> roles = user.getRoles();
		Set<SYSMenu> menus = new HashSet<SYSMenu>();
		for (SYSRole role : roles) {
			menus.addAll(role.getMenus());
		}
		return menus;
	}

}
