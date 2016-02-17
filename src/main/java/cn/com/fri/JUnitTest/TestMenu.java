package cn.com.fri.JUnitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.fri.JUnitTest.base.JUnitBaseBiz;
import cn.com.fri.basic.biz.IBaseSQLBiz;
import cn.com.fri.common.biz.IFileBiz;
import cn.com.fri.sys.biz.ISYSAttachedBiz;
import cn.com.fri.sys.biz.ISYSDictionaryBiz;
import cn.com.fri.sys.biz.ISYSMenuBiz;
import cn.com.fri.sys.dto.TreeDicDto;
import cn.com.fri.sys.po.SYSAttached;
import cn.com.fri.sys.po.SYSDictionary;
import cn.com.fri.sys.utils.TreeUtil;

public class TestMenu extends JUnitBaseBiz {

	@Autowired
	private ISYSDictionaryBiz biz;

	@Autowired
	private ISYSAttachedBiz attBiz;

	@Autowired
	private IFileBiz fileBiz;

	@Autowired
	private ISYSMenuBiz menuBiz;

	@Autowired
	private ISYSMenuBiz mbiz;

	@Autowired
	private ISYSAttachedBiz attachedBiz;

	@Autowired
	private IBaseSQLBiz sqlBiz;

	@Test
	public void find023() {
		String sql = "SELECT SUM(t.enabled) AS SUM,sum(t.locked) AS lo FROM sys_user t";
		List list = sqlBiz.findBySql(sql);
		for (Object d : list) {
			Object[] ob = (Object[]) d;
			if (ob[0] != null) {
				System.out.println((ob[0].toString()));
			}
		}
	}

	@Test
	public void find01() {
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("menus", mbiz.findMenusByUser("user3"));
		String[] l = { "size" };
		SYSAttached tt = new SYSAttached();
		tt.setUploadUser("user3");
		List<SYSAttached> t = attachedBiz.findByExample(tt, l);
		// if (null != t && t.size() > 0) {
		// map.put("logourl", t.get(0));
		// }
		this.printJson(t);
	}

	// // 系统附件地址常量
	// private String savePath = SYSInitValues.SAVEPATH;

	// @Test
	// public void find3() {
	// log.info(savePath);
	// }
	//
	// @Test
	// public void find() {
	// Page<SYSDictionary> p = new Page<SYSDictionary>();
	// p.setCurrentPage(1);
	// p.setPageSize(50);
	// p.getQueryParams()
	// .put("parentcode", "402883e0493fca0c01494020220f0002");
	// this.printJson(biz.findByPage(p, null, null, null, null, null, null,
	// false));
	// }
	//
	// @Test
	// public void find2() {
	// SYSAttached t = attBiz.findById("402883e04b4d38e6014b4d3aca2d0002");
	// this.printJson(t);
	// }
	//
	// @Test
	// public void deleteFile() {// 402883e04b6c3d70014b6c3e03050000
	// log.info("---->>>" + fileBiz.deleteFolder("", "D:/ZTBAttached/files/a"));
	// }

	@Test
	public void find3() throws IllegalArgumentException, IllegalAccessException {
		List<SYSDictionary> root = biz.findByProperty("parent", "root");
		List<TreeDicDto> t = new ArrayList<TreeDicDto>();
		if (null != root && root.size() > 0) {
			for (SYSDictionary d : root) {
				TreeDicDto rd = new TreeDicDto();
				TreeUtil.convertToTree(rd, d);
				List<SYSDictionary> dic = biz.findByProperty("parent",
						rd.getId());
				if (dic == null || dic.size() == 0) {
					rd.setLeaf(true);
				} else {
					rd.setLeaf(false);
				}
				rd.setExpanded(false);
				t.add(rd);
			}
		}
		this.printJson(t);
	}

}
