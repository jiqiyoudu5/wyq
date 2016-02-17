package cn.com.fri.sys.biz;

import cn.com.fri.basic.biz.IBaseHibernateBiz;
import cn.com.fri.sys.po.SYSRole;

public interface ISYSRoleBiz extends IBaseHibernateBiz<SYSRole> {

	/**
	 * RESOURCES与ROLE关联
	 * 
	 * @param roleId
	 * @param resIds
	 */
	public void configRoleResources(String roleId, String[] resIds);

	/**
	 * Users与ROLE关联
	 * 
	 * @param roleId
	 * @param resIds
	 */
	public void configRoleUsers(String roleId, String[] userIds);

	/**
	 * 删除RESOURCES与ROLE关联
	 * 
	 * @param roleId
	 * @param userIds
	 */
	public void removeRoleResources(String roleId, String[] resIds);

	/**
	 * 删除USERS与ROLE关联
	 * 
	 * @param roleId
	 * @param userIds
	 */
	public void removeRoleUsers(String roleId, String[] userIds);

	/**
	 * Menus与ROLE关联
	 * 
	 * @param roleId
	 * @param menuIds
	 */
	public void configRoleMenus(String roleId, String[] menuIds);

}
