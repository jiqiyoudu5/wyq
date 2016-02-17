package cn.com.fri.spring.security.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.fri.basic.controller.BaseController;
import cn.com.fri.spring.security.utils.LoginUser;

/**
 * 系统登录及登录成功后初始化基本信息 ++ 请求失败跳转提示页面
 * 
 * @author WYQ
 * 
 */
@Controller
public class SYSConfigToPageController extends BaseController {

	@Autowired
	protected SessionRegistry registry;

	@RequestMapping(value = "/login")
	public String forwardLogin() {
		return "login";
	}

	@RequestMapping(value = "/no-login-forward")
	public ModelAndView NoLoginForward(ModelAndView mv) {
		mv.setViewName("login");
		mv.addObject("message", "您还未登陆系统！");
		return mv;
	}

	@RequestMapping(value = "/login_max_sessions")
	public ModelAndView forwardLoginMaxSessions(ModelAndView mv) {
		mv.setViewName("/login");
		mv.addObject("message", "该账号已被登录，请重新登录！");
		return mv;
	}

	/**
	 * 用户登录成功后判断跳转到的页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/login-success")
	public ModelAndView success_Forward() throws Exception {
		// 获取当前用户的角色，根据角色判断去向
		if (SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/error/error");
			mv.addObject("title", "403");
			mv.addObject("message", "您没有该权限！");
			return mv;
		} else {
			LoginUser userDetails = (LoginUser) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority> roles = userDetails.getAuthorities();
			List<String> str = new ArrayList<String>();
			for (GrantedAuthority role : roles) {
				log.info(">>ROLES:::" + role.toString());
				str.add(role.toString());
			}
			// 选择页面去向
			ModelAndView mv = initLoginParam();
			if (str.contains("ROLE_ADMIN")) {
				mv.setViewName("/sys/home");
			} else if (str.contains("ROLE_USER")) {
				mv.setViewName("/user/home");
			}

			return mv;
		}
	}

	/**
	 * 用户没有权限指向
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/error-403")
	public ModelAndView forward_403(ModelAndView mv) {
		mv.setViewName("/error/error");
		mv.addObject("title", "403");
		mv.addObject("message", "您没有该权限！");
		return mv;
	}

	@RequestMapping(value = "/404")
	public ModelAndView forward_404(ModelAndView mv) {
		mv.setViewName("/error/error");
		mv.addObject("title", "404");
		mv.addObject("message", "找不到该资源！");
		return mv;
	}

	@RequestMapping(value = "/500")
	public ModelAndView forward_500(ModelAndView mv) {
		mv.setViewName("/error/error");
		mv.addObject("title", "500");
		mv.addObject("message", "系统内部错误！");
		return mv;
	}

	private ModelAndView initLoginParam() {
		ModelAndView mv = new ModelAndView();
		log.info("进入 用户主页");
		Map<Object, Date> lastActivityDates = new HashMap<Object, Date>();
		for (Object principal : registry.getAllPrincipals()) {
			for (SessionInformation session : registry.getAllSessions(
					principal, false)) {
				if (lastActivityDates.get(principal) == null) {
					lastActivityDates.put(principal, session.getLastRequest());
				} else {
					Date prevLastRequest = lastActivityDates.get(principal);
					if (session.getLastRequest().after(prevLastRequest)) {
						// update if so
						lastActivityDates.put(principal,
								session.getLastRequest());
					}
				}
			}
		}
		Object o = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		if (o != null && "anonymousUser".equals(o.toString())) {
			mv.setViewName("redirect:../login");
			return mv;
		}
		LoginUser user = (LoginUser) o;
		mv.getModel().put("currentAccount", user);// 当前登录用户
		mv.getModel().put("loggedUsers", lastActivityDates);// 当前系统所有在线用户
		mv.getModel().put("loggedUserCount", lastActivityDates.size());// 当前系统所有在线用户总数
		return mv;
	}

}
