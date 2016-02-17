package cn.com.fri.sys.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.fri.basic.controller.BaseController;
import cn.com.fri.basic.utils.page.Page;
import cn.com.fri.basic.utils.page.PageData;
import cn.com.fri.sys.biz.ISYSUserBiz;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;

@Controller
@RequestMapping("/sys/user")
public class SYSUserController extends BaseController {

	@Autowired
	private ISYSUserBiz biz;

	@ResponseBody
	@RequestMapping(value = "/findall", method = RequestMethod.GET)
	public PageData<SYSUser> findByAll(HttpServletRequest request, int limit,
			int page) {
		String v = request.getParameter("vip");
		String locked = request.getParameter("locked");
		String enabled = request.getParameter("enabled");
		Page<SYSUser> p = new Page<SYSUser>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		if (v != null) {
			Boolean vip = false;
			if (v.equals("1")) {
				vip = true;
			}
			p.getQueryParams().put("vip", vip);
		}
		if (locked != null) {
			Boolean b = false;
			if (locked.equals("1")) {
				b = true;
			}
			p.getQueryParams().put("locked", b);
		}
		if (enabled != null) {
			Boolean b = false;
			if (enabled.equals("1")) {
				b = true;
			}
			p.getQueryParams().put("enabled", b);
		}
		return biz.findByPage(p, "regTime", "desc", null, null, null, null,
				false).getData();
	}

	/**
	 * 
	 * 关键词检索系统用户表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchusersbykeyword", method = RequestMethod.GET)
	public PageData<SYSUser> serchUser(HttpServletRequest request, int limit,
			int page) {
		String keyWord = request.getParameter("keyWord");
		Page<SYSUser> p = new Page<SYSUser>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		if (StringUtils.isNotBlank(keyWord)) {
			p.getQueryParams().put("username", keyWord);
			p.getQueryParams().put("businessName", keyWord);
			p.getQueryParams().put("email", keyWord);
			p.getQueryParams().put("tel", keyWord);
			p.getQueryParams().put("phone", keyWord);
			p.getQueryParams().put("district", keyWord);
			p.getQueryParams().put("address", keyWord);
		}
		return biz.findKeyWordByPage(p, "regTime", "desc", true).getData();
	}

	/**
	 * 多字段查询
	 * 
	 * @return
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public PageData<SYSUser> search(HttpServletRequest request, int limit,
			int page) throws ParseException {
		String username = request.getParameter("username");
		String businessName = request.getParameter("businessName");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String phone = request.getParameter("phone");
		String district = request.getParameter("district");
		String address = request.getParameter("address");
		String enabled = request.getParameter("enabled");
		String locked = request.getParameter("locked");
		String vip = request.getParameter("vip");
		String vipendtime = request.getParameter("vipendtime");
		Page<SYSUser> p = new Page<SYSUser>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		SYSUser user = new SYSUser();
		if (StringUtils.isNotBlank(username)) {
			p.getQueryParams().put("username", username);
		}
		if (StringUtils.isNotBlank(businessName)) {
			user.setBusinessName("%" + businessName + "%");
		}
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		}
		if (StringUtils.isNotBlank(tel)) {
			user.setTel(tel);
		}
		if (StringUtils.isNotBlank(phone)) {
			user.setPhone(phone);
		}
		if (StringUtils.isNotBlank(district)) {
			user.setDistrict(district);
		}
		if (StringUtils.isNotBlank(address)) {
			user.setAddress("%" + address + "%");
		}
		if (StringUtils.isNotBlank(enabled)) {
			boolean b = false;
			if (enabled.equals("true")) {
				b = true;
			}
			user.setEnabled(b);
		}
		if (StringUtils.isNotBlank(locked)) {
			boolean b = false;
			if (locked.equals("true")) {
				b = true;
			}
			user.setLocked(b);
		}
		if (StringUtils.isNotBlank(vip)) {
			boolean b = false;
			if (vip.equals("true")) {
				b = true;
			}
			user.setVip(b);
		}
		if (StringUtils.isNotBlank(vipendtime)) {
			user.setVipendtime(new SimpleDateFormat("yyyy-MM-dd")
					.parse(vipendtime));
		}
		return biz.findByPageExample(p, user, true, true, "regTime", "desc")
				.getData();
	}

	/**
	 * 验证用户是否存在库中
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exists", method = RequestMethod.POST)
	public String exists(HttpServletRequest request) {
		String username = request.getParameter("username");
		Boolean b = false;
		b = biz.exists(username);
		String s = "1";
		if (!b) {
			s = "0";
		}
		return s;
	}

	@ResponseBody
	@RequestMapping(value = "/add/{username}", method = RequestMethod.POST)
	public ModelAndView add(@PathVariable("username") String username,
			@RequestBody SYSUser user) throws ParseException {
		if (biz.exists(username)) {// 判断用户名是否存在
			FAILURE.addObject("message", "用户名已经存在！");
			return FAILURE;
		}
		user.setRegTime(new Date());
		if (null != user.getVipendtime()) {// 减去8小时
			Calendar t = Calendar.getInstance();
			t.setTime(user.getVipendtime());
			t.set(Calendar.HOUR, t.get(Calendar.HOUR) - 8);
			user.setVipendtime(t.getTime());
		}
		biz.save(user);
		return SUCCESS;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView update(HttpServletRequest request) throws Exception {
		SYSUser user = biz.findById(request.getParameter("username"));
		user.setAddress(request.getParameter("address"));
		user.setBusinessName(request.getParameter("businessName"));
		user.setDistrict(request.getParameter("district"));
		user.setEmail(request.getParameter("email"));
		boolean enabled_b = false;
		if (request.getParameter("enabled").equals("true")) {
			enabled_b = true;
		}
		user.setEnabled(enabled_b);
		boolean locked_b = false;
		if (request.getParameter("locked").equals("true")) {
			locked_b = true;
		}
		user.setLocked(locked_b);
		user.setPhone(request.getParameter("phone"));
		user.setRemark(request.getParameter("remark"));
		user.setTel(request.getParameter("tel"));
		boolean vip_b = false;
		if (request.getParameter("vip").equals("true")) {
			vip_b = true;
		}
		user.setVip(vip_b);
		user.setUpdateTime(new Date());
		user.setUpdateUser(this.getUserDetails().getUsername());
		if (StringUtils.isNotBlank(request.getParameter("vipendtime"))) {
			user.setVipendtime(DateUtils.parseDate(
					request.getParameter("vipendtime"),
					new String[] { "yyyy-MM-dd" }));
		}
		// if (null != request.getParameter("vipendtime")) {// 减去8小时
		// Calendar c = Calendar.getInstance();
		// c.setTime(t.getVipendtime);
		// c.set(Calendar.HOUR, c.get(Calendar.HOUR) - 8);
		// user.setVipendtime(c.getTime());
		// }
		biz.update(user);
		return SUCCESS;
	}

	/**
	 * 提供给管理员修改用户密码用
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updatePassword(String username, String password)
			throws Exception {
		SYSUser user = biz.findById(username);
		MessageDigestPasswordEncoder me = new MessageDigestPasswordEncoder(
				"MD5");
		me.setEncodeHashAsBase64(false);
		user.setPassword(me.encodePassword(password.trim(), username.trim()));
		user.setUpdateTime(new Date());
		user.setUpdateUser(this.getUserDetails().getUsername());
		biz.update(user);
		return SUCCESS;
	}

	@RequestMapping(value = "/delete/{username}", method = RequestMethod.DELETE)
	@ResponseBody
	public ModelAndView delete(@PathVariable("username") String username,
			@RequestBody SYSUser user) {
		biz.delete(user.getUsername());
		return SUCCESS;
	}

	/**
	 * 查看7天后VIP到期的用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findexpirevipusers", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSUser> findExpireVipUser(int page, int limit) {
		Page<SYSUser> p = new Page<SYSUser>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT t.* FROM sys_user t WHERE '"
				+ formatDate.format(date) + "' <= t.vipendtime ";
		long t = date.getTime() + 24 * 60 * 60 * 7 * 1000;// 加7天
		date.setTime(t);
		sql += "AND t.vipendtime < '" + formatDate.format(date)
				+ "' AND vip = 1";
		return biz.findPageBySql(p, sql).getData();
	}

	/**
	 * 查看已到期VIP用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findendvipusers", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSUser> findEndVipUser(int page, int limit) {
		Page<SYSUser> p = new Page<SYSUser>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT t.* FROM sys_user t WHERE '"
				+ formatDate.format(date) + "' >= t.vipendtime and vip = 1";
		return biz.findPageBySql(p, sql).getData();
	}

	/**
	 * 查询用户roles
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/finduserrolesbyuser", method = RequestMethod.GET)
	@ResponseBody
	public Set<SYSRole> findRolesByUsername(HttpServletRequest request)
			throws Exception {
		String username = request.getParameter("username");
		return biz.findUserRolesByUsername(username);
	}

	/**
	 * VIP转换为普通用户并解除VIP角色权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "/removevippower", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView removeVipPower(HttpServletRequest request) {
		String roles[] = request.getParameter("ids").split(",");
		String username = request.getParameter("username");
		if (roles.length > 0) {
			biz.removeVipPower(roles, username);
		}
		return SUCCESS;
	}

}
