package cn.com.fri.sys.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.fri.basic.controller.BaseController;
import cn.com.fri.basic.utils.page.M2MFindByPage;
import cn.com.fri.basic.utils.page.Page;
import cn.com.fri.basic.utils.page.PageData;
import cn.com.fri.spring.security.customFilters.MySecurityMetadataSource;
import cn.com.fri.sys.biz.ISYSMenuBiz;
import cn.com.fri.sys.biz.ISYSResourceBiz;
import cn.com.fri.sys.biz.ISYSRoleBiz;
import cn.com.fri.sys.biz.ISYSUserBiz;
import cn.com.fri.sys.po.SYSMenu;
import cn.com.fri.sys.po.SYSResource;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;

@Controller
@RequestMapping("/sys/role")
public class SYSRoleController extends BaseController {

	@Autowired
	private ISYSRoleBiz biz;

	@Autowired
	private ISYSResourceBiz resBiz;

	@Autowired
	private ISYSUserBiz userBiz;

	@Autowired
	private ISYSMenuBiz menuBiz;

	@Autowired
	@Qualifier("mySecurityMetadataSource")
	private MySecurityMetadataSource sms;

	@RequestMapping(value = "/findall", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSRole> findAll(int limit, int page) {
		Page<SYSRole> p = new Page<SYSRole>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		return biz.findByPage(p, null, null, null, null, null, null, false)
				.getData();
	}

	@RequestMapping(value = "/exists", method = RequestMethod.POST)
	@ResponseBody
	public String exists(HttpServletRequest request) {
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		List<SYSRole> res = null;
		if (StringUtils.isNotBlank(name)) {
			res = biz.findByProperty("name", name.trim());
		}
		if (StringUtils.isNotBlank(code)) {
			res = biz.findByProperty("code", code.trim());
		}
		String s = "0";
		if (null != res && res.size() > 0) {
			s = "1";
		}
		return s;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView add(@RequestBody SYSRole role) {
		biz.save(role);
		return SUCCESS;
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ModelAndView update(@PathVariable("id") String id,
			@RequestBody SYSRole t) {
		SYSRole role = biz.findById(t.getId());
		role.setCode(t.getCode());
		role.setName(t.getName());
		role.setRemark(t.getRemark());
		biz.update(role);
		return SUCCESS;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ModelAndView delete(@PathVariable("id") String id) {
		biz.delete(id);
		return SUCCESS;
	}

	/**
	 * 查询已分配给角色的资源
	 * 
	 * @param request
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/findresourcebyroleid", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSResource> findByRoleId(HttpServletRequest request,
			int limit, int page) {
		String roleId = request.getParameter("roleId");
		Page<SYSResource> p = new Page<SYSResource>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		M2MFindByPage param = new M2MFindByPage();
		param.setAliasName("roles");
		param.setAliasValue("role");
		param.getMainTableQueryParams().put("role.id", roleId);
		return resBiz.findByPage(p, "endUpdateTime", "desc", null, null, null,
				param, false).getData();
	}

	/**
	 * 查询未分配的资源
	 * 
	 * @param request
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/findnoconfigroleresource", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSResource> findResourcePageBySql(
			HttpServletRequest request, int limit, int page) {
		String roleId = request.getParameter("roleId");
		Page<SYSResource> p = new Page<SYSResource>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		String sqlWhere = "(SELECT t.resourceid FROM sys_role_resource t WHERE t.roleid = '"
				+ roleId + "')";
		String sqlMain = "SELECT * FROM sys_resource a WHERE a.id NOT IN "
				+ sqlWhere;
		return resBiz.findPageBySql(p, sqlMain).getData();
	}

	/**
	 * 资源与角色关联
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/configroleresources", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView configRoleResources(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String[] resIds = request.getParameterValues("ids");
		biz.configRoleResources(roleId, resIds);
		return SUCCESS;
	}

	/**
	 * 删除资源与角色关系
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/removeroleresource", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView removeRoleResource(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String[] resIds = request.getParameterValues("ids");
		biz.removeRoleResources(roleId, resIds);
		return SUCCESS;
	}

	/**
	 * 查询已分配给角色的用户
	 * 
	 * @param request
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/findusersbyroleid", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSUser> findRoleUsersByRoleId(HttpServletRequest request,
			int limit, int page) {
		String roleId = request.getParameter("roleId");
		Page<SYSUser> p = new Page<SYSUser>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		M2MFindByPage param = new M2MFindByPage();
		param.setAliasName("roles");
		param.setAliasValue("role");
		param.getMainTableQueryParams().put("role.id", roleId);
		return userBiz.findByPage(p, "regTime", "desc", null, null, null,
				param, false).getData();
	}

	/**
	 * 查询未分配的用户
	 * 
	 * @param request
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/findnoconfigroleusers", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSUser> findUsersPageBySql(HttpServletRequest request,
			int limit, int page) {
		String roleId = request.getParameter("roleId");
		String vip = request.getParameter("vip");
		String locked = request.getParameter("locked");
		String enabled = request.getParameter("enabled");
		String keyWord = request.getParameter("keyWord");
		Page<SYSUser> p = new Page<SYSUser>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		String where1 = null;
		if (vip != null) {
			Boolean b = false;
			if (vip.equals("1")) {
				b = true;
			}
			where1 = "a.vip=" + b;
		}
		if (locked != null) {
			Boolean b = false;
			if (locked.equals("1")) {
				b = true;
			}
			where1 = "a.locked=" + b;
		}
		if (enabled != null) {
			Boolean b = false;
			if (enabled.equals("1")) {
				b = true;
			}
			where1 = "a.enabled=" + b;
		}
		String where2 = "(SELECT t.username FROM sys_user_role t WHERE t.roleid = '"
				+ roleId + "')";
		String sqlMain = "SELECT * FROM sys_user a WHERE a.username NOT IN "
				+ where2;

		if (StringUtils.isNotBlank(where1)) {
			sqlMain += " and " + where1;
		}
		if (StringUtils.isNotBlank(keyWord)) {
			String likeValue = "'%" + keyWord + "%'";
			String sql = " and ( a.username like " + likeValue;
			sql += " or a.businessname like " + likeValue;
			sql += " or a.phone like " + likeValue;
			sql += " or a.email like " + likeValue;
			sql += " or a.tel like " + likeValue;
			sql += " or a.address like " + likeValue;
			sql += " or a.district like " + likeValue + " )";
			sqlMain += sql;
		}
		return userBiz.findPageBySql(p, sqlMain).getData();
	}

	/**
	 * 
	 * 用户与角色关联
	 * 
	 * @return
	 */
	@RequestMapping(value = "/configroleusers", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView configRoleUsers(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String[] usernames = request.getParameterValues("ids");
		biz.configRoleUsers(roleId, usernames);
		return SUCCESS;
	}

	/**
	 * 删除用戶与角色关系
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/removeroleuser", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView removeRoleUser(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String[] usernames = request.getParameterValues("ids");
		biz.removeRoleUsers(roleId, usernames);
		return SUCCESS;
	}

	/**
	 * 根据ROLE的ID查询已分配给角色的菜单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findroletreebyroleid", method = RequestMethod.POST)
	@ResponseBody
	public Set<SYSMenu> findRoleMenuTreeByRoleId(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		return menuBiz.findMenuByRoleId(roleId);
	}

	/**
	 * 菜单MENUS与角色ROLE关联
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/configrolemenus", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView configRoleMenus(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String[] menuIds = request.getParameterValues("ids");
		biz.configRoleMenus(roleId, menuIds);
		return SUCCESS;
	}

	/**
	 * 刷新SECUTITY内存中资源与角色信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/refreshresourcemap", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView refreshResourceMap() {
		sms.refresh();
		return SUCCESS;
	}

}
