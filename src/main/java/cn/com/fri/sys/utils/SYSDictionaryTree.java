package cn.com.fri.sys.utils;

import java.util.ArrayList;
import java.util.List;

import cn.com.fri.sys.po.SYSDictionary;

public class SYSDictionaryTree extends SYSDictionary {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8562171552558268746L;

	private List<SYSDictionaryTree> children = new ArrayList<SYSDictionaryTree>();

	public List<SYSDictionaryTree> getChildren() {
		return children;
	}

	public void setChildren(List<SYSDictionaryTree> children) {
		this.children = children;
	}

}
