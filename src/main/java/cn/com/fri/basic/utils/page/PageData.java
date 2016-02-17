package cn.com.fri.basic.utils.page;

import java.util.List;

/**
 * 分页数据，用于拼装JSON
 * 
 * @since Spring 4.0.3, Hibernate 4.3.5 and JDK 1.7
 * @param <T>
 * 
 */
public class PageData<T> {

	private long totalCount;

	private List<T> items;

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
