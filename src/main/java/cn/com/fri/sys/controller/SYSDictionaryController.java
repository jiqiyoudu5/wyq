package cn.com.fri.sys.controller;

import java.lang.reflect.InvocationTargetException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.fri.basic.controller.BaseController;
import cn.com.fri.basic.utils.page.Page;
import cn.com.fri.basic.utils.page.PageData;
import cn.com.fri.sys.biz.ISYSDictionaryBiz;
import cn.com.fri.sys.dto.TreeDicDto;
import cn.com.fri.sys.po.SYSDictionary;
import cn.com.fri.sys.utils.SYSDictionaryTree;

@Controller
@RequestMapping("/sys/dictionary")
public class SYSDictionaryController extends BaseController {

	@Autowired
	private ISYSDictionaryBiz biz;

	/**
	 * 一次加载完树结构(已经停用！！！！）
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/finddictree", method = RequestMethod.GET)
	@ResponseBody
	public SYSDictionaryTree findSYSDicTree() throws IllegalAccessException,
			InvocationTargetException {
		return biz.findSYSDictionaryTree();
	}

	/**
	 * 树-异步
	 * 
	 * @param parentId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/tree/ajax/{parentId}", method = { RequestMethod.GET })
	@ResponseBody
	public List<TreeDicDto> treeListByParentId(
			@PathVariable("parentId") String parentId)
			throws IllegalAccessException, InvocationTargetException {
		return biz.treeListByParentId(parentId);
	}

	/**
	 * type查具体某类字典 parentCode=“parent”查字典所有分类 text查具体某个字典（字典名称）
	 * 
	 * @param limit
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findbypage", method = RequestMethod.GET)
	@ResponseBody
	public PageData<SYSDictionary> findByPage(int limit, int page,
			HttpServletRequest request) {
		String parentCode = request.getParameter("parentCode");
		String keyWord = request.getParameter("keyWord");
		Page<SYSDictionary> p = new Page<SYSDictionary>();
		p.setCurrentPage(page);
		p.setPageSize(limit);
		if (StringUtils.isNotBlank(keyWord)
				&& StringUtils.isNotBlank(parentCode)) {
			p.getQueryParams().put("text", keyWord);
			p.getQueryParams().put("remark", keyWord);
			Map<String, Object> mapIs = new HashMap<String, Object>();
			mapIs.put("parent", parentCode);
			return biz.findKeyWordByPage_S(p, true, mapIs, null, null, null,
					null, null, null, null).getData();
		} else {
			if (StringUtils.isNotBlank(parentCode)) {
				p.getQueryParams().put("parent", parentCode);
			}
			return biz.findByPage(p, null, null, null, null, null, null, false)
					.getData();
		}
	}

	@RequestMapping(value = "/exists", method = RequestMethod.POST)
	@ResponseBody
	public String exists(HttpServletRequest request) {
		String type = request.getParameter("type");
		String text = request.getParameter("text");
		String propertyName = "text";
		String value = text;
		if (StringUtils.isNotBlank(type)) {
			propertyName = "type";
			value = type;
		}
		List<SYSDictionary> res = biz.findByProperty(propertyName, value);
		String s = "1";
		if (res.size() <= 0) {
			s = "0";
		}
		return s;
	}

	// 各个类型的分类查询
	@RequestMapping(value = "/type", method = { RequestMethod.GET })
	@ResponseBody
	public List<SYSDictionary> nations(String type, String text,
			String parentcode) {
		String sql = "select * from sys_dictionary t where t.parent != '"
				+ parentcode + "' and  t.type='" + type.toUpperCase() + "'";
		if (StringUtils.isNotBlank(text)) {
			sql += " and t.text like '" + text + "%'";
		}
		log.info(sql);
		return biz.findListBySql(sql);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void add(@RequestBody SYSDictionary t) {
		t.setType(t.getType().toUpperCase());
		biz.save(t);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@PathVariable("id") String id,
			@RequestBody SYSDictionary t) {
		SYSDictionary d = biz.findById(id);
		String newType = t.getType().toUpperCase();
		boolean b = false;
		if (StringUtils.isNotBlank(t.getType())
				&& StringUtils.equals(d.getParent(), "wyq8d8i8c8t8y8p8e")
				&& !StringUtils.equals(newType, d.getType())) {
			d.setType(newType);
			b = true;
		}
		if (StringUtils.isNotBlank(t.getText())) {
			d.setText(t.getText());
		}
		if (StringUtils.isNotBlank(t.getParent())) {
			d.setParent(t.getParent());
		}
		d.setRemark(t.getRemark());
		biz.update(d, b);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView deleteDic(HttpServletRequest request) {
		String type = request.getParameter("type");
		String[] ids = request.getParameterValues("ids");
		if (StringUtils.isNotBlank(type) && null != ids && ids.length > 0) {
			biz.deleteDicType(ids);
		} else {
			biz.deleteIn("id", ids);
		}
		return SUCCESS;
	}

}
