package cn.com.fri.sys.controller;

import java.lang.reflect.InvocationTargetException;
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
import cn.com.fri.sys.biz.ISYSMenuBiz;
import cn.com.fri.sys.po.SYSMenu;
import cn.com.fri.sys.utils.MenuTree;

@Controller
@RequestMapping("/sys/menu")
public class SYSMenuController extends BaseController {

	@Autowired
	private ISYSMenuBiz biz;

	@RequestMapping(value = "/byuserline", method = RequestMethod.GET)
	@ResponseBody
	public MenuTree findTreeByUser() throws Exception {
		String username = this.getUserDetails().getUsername();
		return biz.findMenuTreeByUser(username);
	}

	@RequestMapping(value = "/findall", method = RequestMethod.GET)
	@ResponseBody
	public MenuTree findAll() throws IllegalAccessException,
			InvocationTargetException {
		return biz.findMenuTree();
	}

	@RequestMapping(value = "/exists", method = RequestMethod.POST)
	@ResponseBody
	public String exists(HttpServletRequest request) {
		String value = request.getParameter("value");
		String s = "1";
		List<SYSMenu> menus = biz.findByProperty("value", value);
		if (menus.size() <= 0) {
			s = "0";// 不存在
		}
		return s;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView save(@RequestBody SYSMenu menu) {
		if (StringUtils.isNotBlank(menu.getIcon())) {
			menu.setIcon("file/img/" + menu.getIcon());
		}
		biz.save(menu);
		return SUCCESS;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ModelAndView delete(@PathVariable("id") String id) {
		biz.delete(id);
		return SUCCESS;
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ModelAndView update(@PathVariable("id") String id,
			@RequestBody SYSMenu menu) {
		SYSMenu t = biz.findById(id);
		t.setExpanded(menu.isExpanded());
		t.setLeaf(menu.isLeaf());
		if (StringUtils.isNotBlank(menu.getIcon())) {
			t.setIcon("file/img/" + menu.getIcon());
		} else {
			t.setIcon(menu.getIcon());
		}
		t.setOrderValue(menu.getOrderValue());
		t.setRemark(menu.getRemark());
		t.setText(menu.getText());
		t.setValue(menu.getValue());
		t.setViewType(menu.getViewType());
		biz.update(t);
		return SUCCESS;
	}

}
