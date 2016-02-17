package cn.com.fri.basic.utils.page;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页时多对多查询条件属性 使用方法： M2MFindByPage param = new M2MFindByPage();
 * param.setAliasName("Set<对方表Entity>的对象名，例子名deptmetnts");
 * param.setAliasValue("aliasName自定义别名，列子别名dept");
 * 
 * param.getMainTableQueryParams().put("dept.id", 条件值);
 * 
 * @author WYQ
 * 
 */
public class M2MFindByPage {

	/**
	 * Entity中设置多对多关系对方表的Set对象名
	 */
	private String aliasName;

	/**
	 * 可自定义aliasName的值
	 */
	private String aliasValue;

	private String mainTableFieldName;

	private Object mainTableFieldValue;

	/**
	 * 对方表中查询条件字段值、字段设置
	 */
	private Map<String, Object> mainTableQueryParams = new HashMap<String, Object>();

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getAliasValue() {
		return aliasValue;
	}

	public void setAliasValue(String aliasValue) {
		this.aliasValue = aliasValue;
	}

	public String getMainTableFieldName() {
		return mainTableFieldName;
	}

	public void setMainTableFieldName(String mainTableFieldName) {
		this.mainTableFieldName = mainTableFieldName;
	}

	public Object getMainTableFieldValue() {
		return mainTableFieldValue;
	}

	public void setMainTableFieldValue(Object mainTableFieldValue) {
		this.mainTableFieldValue = mainTableFieldValue;
	}

	public Map<String, Object> getMainTableQueryParams() {
		return mainTableQueryParams;
	}

	public void setMainTableQueryParams(Map<String, Object> mainTableQueryParams) {
		this.mainTableQueryParams = mainTableQueryParams;
	}

}
