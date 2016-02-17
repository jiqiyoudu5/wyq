package cn.com.fri.sys.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.fri.basic.controller.BaseController;
import cn.com.fri.basic.utils.page.Page;
import cn.com.fri.basic.utils.page.PageData;
import cn.com.fri.sys.biz.ISYSAttachedBiz;
import cn.com.fri.sys.po.SYSAttached;

/**
 * 平台附件管理
 * 
 * @author WYQ
 * 
 */
@Controller
@RequestMapping("/sys/attached")
public class SYSAttachedController extends BaseController {

	@Autowired
	private ISYSAttachedBiz biz;

	@RequestMapping(value = "/findbypage", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSAttached> findByPage(int limit, int page,
			HttpServletRequest request) {
		// String name = request.getParameter("name");
		// String uploadUser = request.getParameter("uploadUser");
		// String dataFrom = request.getParameter("dataFrom");
		String type = request.getParameter("type");
		// String size = request.getParameter("size");
		String shzt = request.getParameter("shzt");

		String postfix = request.getParameter("postfix");

		String[] postfixs = request.getParameterValues("postfixs");

		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		Date bTime = null;
		Date eTime = null;
		if (StringUtils.isNotBlank(beginTime)) {
			bTime = Timestamp.valueOf(beginTime.substring(0,
					beginTime.lastIndexOf("T00:00:00"))
					+ " 00:00:00");
		}
		if (StringUtils.isNotBlank(endTime)) {
			eTime = Timestamp.valueOf(endTime.substring(0,
					endTime.lastIndexOf("T00:00:00"))
					+ " 23:59:59");
		}

		Page<SYSAttached> p = new Page<SYSAttached>();
		p.setPageSize(limit);
		p.setCurrentPage(page);

		if (StringUtils.isNotBlank(postfix)) {
			p.getQueryParams().put("postfix", postfix.toLowerCase());
		}
		if (StringUtils.isNotBlank(type)) {
			p.getQueryParams().put("type", type);
		}
		Map<String, Object> mapIn = new HashMap<String, Object>();
		if (null != postfixs && postfixs.length > 1) {
			mapIn.put("postfix", postfixs);
		}
		if (StringUtils.isNotBlank(shzt)) {
			p.getQueryParams().put("shzt", shzt);
		}

		return biz.findKeyWordByPage_S(p, false, mapIn, null, "uploadTime",
				bTime, eTime, null, "uploadTime", "desc").getData();
	}

	@RequestMapping(value = "/findbylist", method = RequestMethod.GET)
	@ResponseBody
	public List<SYSAttached> findByList(HttpServletRequest request) {
		String type = request.getParameter("type");
		List<SYSAttached> list = biz.findByProperty("type", type, "uploadTime",
				"desc");
		return list;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ModelAndView update(@PathVariable("id") String id,
			@RequestBody SYSAttached t) {
		SYSAttached a = biz.findById(id);
		if (StringUtils.isNotBlank(t.getName())) {
			a.setName(t.getName());
		}
		if (StringUtils.isNotBlank(t.getRemark())) {
			a.setRemark(t.getRemark());
		}
		if (StringUtils.isNotBlank(t.getShzt())) {

		}
		if (StringUtils.isNotBlank(t.getName())) {
			a.setShzt(t.getShzt());
		}
		if (StringUtils.isNotBlank(t.getType())) {
			a.setType(t.getType());
		}
		biz.update(a);
		return SUCCESS;
	}

	@RequestMapping(value = "/shzt", method = RequestMethod.POST)
	public ModelAndView updateByIds(@RequestParam("ids") String[] ids,
			@RequestParam("shzt") String shzt) {
		biz.updateIn("shzt", shzt, "id", ids);
		return SUCCESS;
	}
}
