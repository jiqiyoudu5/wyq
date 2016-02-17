package cn.com.fri.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.fri.basic.controller.BaseController;
import cn.com.fri.sys.biz.ISYSDatabaseBackAndRestoreBiz;
import cn.com.fri.sys.utils.SYSInitValues;

@Controller
@RequestMapping("/sys/db")
public class SYSDatabaseBackAndRestoreController extends BaseController {

	// 系统附件地址常量
	private String savePath = SYSInitValues.SAVEPATH;

	private String SYSDBIP = SYSInitValues.SYSDBIP;

	// 端口
	private String SYSDBPORT = SYSInitValues.SYSDBPORT;

	private String SYSDBSQLBINPATH = SYSInitValues.SYSDBSQLBINPATH;

	private String SYSDBUSER = SYSInitValues.SYSDBUSER;

	private String SYSDBPASSWORD = SYSInitValues.SYSDBPASSWORD;

	private String SYSDBNAME = SYSInitValues.SYSDBNAME;

	@Autowired
	private ISYSDatabaseBackAndRestoreBiz biz;

	@RequestMapping(value = "/back", method = RequestMethod.GET)
	@ResponseBody
	public void dbback(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		biz.backdb(savePath, null, SYSDBSQLBINPATH, SYSDBIP, SYSDBPORT,
				SYSDBUSER, SYSDBPASSWORD, SYSDBNAME, this.getUserDetails()
						.getUsername());
	}

	@RequestMapping(value = "/back/io", method = RequestMethod.GET)
	@ResponseBody
	public void dbback_IO(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		biz.backdb_IO(savePath, "123.sql", SYSDBSQLBINPATH, SYSDBIP, SYSDBPORT,
				SYSDBUSER, SYSDBPASSWORD, SYSDBNAME, this.getUserDetails()
						.getUsername());

	}

	@RequestMapping(value = "/back/view", method = RequestMethod.GET)
	public ModelAndView openDBBAckView(ModelAndView mv) {
		mv.setViewName("/sys/dbbackview");
		return mv;
	}

}
