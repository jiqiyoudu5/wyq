package cn.com.fri.sys.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.fri.basic.dao.impl.BaseHibernateDaoImpl;
import cn.com.fri.sys.dao.ISYSMenuDao;
import cn.com.fri.sys.po.SYSMenu;

/**
 * @author WYQ
 * 
 *         系统菜单树 DAO层接口实现
 * 
 */

@Repository("sysMenuDaoImpl")
public class SYSMenuDaoImpl extends BaseHibernateDaoImpl<SYSMenu> implements
		ISYSMenuDao {

}
