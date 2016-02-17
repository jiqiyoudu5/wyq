package cn.com.fri.basic.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import cn.com.fri.spring.security.utils.LoginUser;

/**
 * @author WYQ
 * 
 */
@Controller
public class BaseController {

	/** 取得日志记录器log */
	protected Logger log = Logger.getLogger(this.getClass().getName());

	@Autowired
	protected SessionRegistry registry;

	/**
	 * 映射成JSON后是{success:true}
	 */
	protected static final ModelAndView SUCCESS = new ModelAndView();

	protected static ModelAndView FAILURE = new ModelAndView();

	public BaseController() {
		SUCCESS.setView(new MappingJackson2JsonView());
		SUCCESS.addObject("success", true);
		FAILURE.setView(new MappingJackson2JsonView());
		FAILURE.addObject("success", false);
	}

	/**
	 * 获取当前系统的用户Detail，注意：不是SYSUser而是LoginUser。
	 * 
	 * @see LoginUser
	 * @return 当前登陆用户的LoginUser实例对象
	 * @throws Exception
	 * 
	 */
	protected LoginUser getUserDetails() throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
			throw new Exception("您无权访问该信息");
		} else {
			LoginUser userDetails = (LoginUser) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			return userDetails;
		}
	}

}