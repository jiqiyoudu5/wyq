package cn.com.fri.basic.utils.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询参数 data属性是一个PageData对象，便于解析JSON
 * 
 * @since JDK 1.7
 * @param <T>
 *            要封装的类
 */
public class Page<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 278149711650513807L;

	public Page() {
	}

	public Page(int pageSize, int currentPage) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}

	/**
	 * 每页显示数据数量
	 */
	private Integer pageSize;// 每页显示多少条

	/**
	 * 当前第几页
	 */
	private Integer currentPage;

	/**
	 * 查询参数
	 */
	private Map<String, Object> queryParams = new HashMap<String, Object>();

	/**
	 * 最终的结果集
	 */
	private PageData<T> data = new PageData<T>();

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PageData<T> getData() {
		return data;
	}

	public void setData(PageData<T> data) {
		this.data = data;
	}
}
