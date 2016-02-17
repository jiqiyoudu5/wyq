package cn.com.fri.sys.biz;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import cn.com.fri.basic.biz.IBaseHibernateBiz;
import cn.com.fri.sys.po.SYSMenu;
import cn.com.fri.sys.utils.MenuTree;

/**
 * @author WYQ
 * 
 *         系统菜单操作业务层接口
 * 
 */
public interface ISYSMenuBiz extends IBaseHibernateBiz<SYSMenu> {

	/**
	 * 查询系统全部菜单(不分权限-->>>慎用)
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public MenuTree findMenuTree() throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 
	 * 根据在线用户查找菜单树
	 * 
	 * @param userLine
	 * @return
	 */
	public MenuTree findMenuTreeByUser(String userLine)
			throws IllegalAccessException, InvocationTargetException;

	/**
	 * 根据在线用户查找菜单(非树结构)
	 * 
	 * @param userLine
	 * @return
	 */
	public Set<SYSMenu> findMenusByUser(String userLine);

	/**
	 * 根据ROLEID查找菜单MENUS
	 * 
	 * @param roleId
	 * @return
	 */
	public Set<SYSMenu> findMenuByRoleId(String roleId);

}
