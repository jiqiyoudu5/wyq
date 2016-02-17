package cn.com.fri.sys.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import cn.com.fri.sys.biz.ISYSResourceBiz;
import cn.com.fri.sys.po.SYSResource;

@Controller
@RequestMapping("/sys/resource")
public class SYSResourceController extends BaseController {

	@Autowired
	private ISYSResourceBiz biz;

	@RequestMapping(value = "/findall", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSResource> findByPage(HttpServletRequest request,
			int limit, int page) {
		String resourceName = request.getParameter("name");
		Page<SYSResource> p = new Page<SYSResource>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		if (StringUtils.isNotBlank(resourceName)) {
			p.getQueryParams().put("name", resourceName);
		}
		return biz.findByPage(p, "endUpdateTime", "desc", null, null, null,
				null, true).getData();
	}

	@RequestMapping(value = "/exists", method = RequestMethod.POST)
	@ResponseBody
	public String exists(HttpServletRequest request) {
		String url = request.getParameter("url");
		List<SYSResource> res = biz.findByProperty("url", url);
		String s = "1";
		if (res.size() <= 0) {
			s = "0";
		}
		return s;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView add(@RequestBody SYSResource t) throws Exception {
		t.setEndRegUser(getUserDetails().getUsername());
		t.setEndUpdateTime(new Date());
		biz.save(t);
		return SUCCESS;
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ModelAndView update(@PathVariable("id") String id,
			@RequestBody SYSResource t) throws Exception {
		SYSResource rce = biz.findById(id);
		rce.setName(t.getName());
		rce.setRemark(t.getRemark());
		rce.setUrl(t.getUrl());
		rce.setEndRegUser(getUserDetails().getUsername());
		rce.setEndUpdateTime(new Date());
		biz.update(rce);
		return SUCCESS;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ModelAndView delete(@PathVariable("id") String id) {
		biz.delete(id);
		return SUCCESS;
	}
}
