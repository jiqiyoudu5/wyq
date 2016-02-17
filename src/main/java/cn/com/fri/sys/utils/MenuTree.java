package cn.com.fri.sys.utils;

import java.util.ArrayList;
import java.util.List;

import cn.com.fri.sys.po.SYSMenu;

public class MenuTree extends SYSMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3565544508346205527L;

	private List<MenuTree> children = new ArrayList<MenuTree>();

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

}
