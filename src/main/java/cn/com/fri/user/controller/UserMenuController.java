package cn.com.fri.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.fri.basic.controller.BaseController;
import cn.com.fri.sys.biz.ISYSAttachedBiz;
import cn.com.fri.sys.biz.ISYSMenuBiz;
import cn.com.fri.sys.po.SYSAttached;

@Controller
@RequestMapping("/user/menu")
public class UserMenuController extends BaseController {

	@Autowired
	private ISYSMenuBiz biz;

	@Autowired
	private ISYSAttachedBiz attachedBiz;

	@RequestMapping(value = "/byuserlinefindlist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findListByUser() throws Exception {
		String username = this.getUserDetails().getUsername();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menus", biz.findMenusByUser(username));
		SYSAttached att = new SYSAttached();
		att.setRelateId(username);
		att.setShzt("1");
		String[] st = { "size" };
		List<SYSAttached> t = attachedBiz.findByExample(att, st);
		if (null != t && t.size() > 0) {
			map.put("logourl", t.get(0).getUrl());
		}
		return map;
	}

}
