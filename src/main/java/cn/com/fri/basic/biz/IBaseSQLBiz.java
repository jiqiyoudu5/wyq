package cn.com.fri.basic.biz;

import java.util.List;

/**
 * @author WYQ
 * 
 */
public interface IBaseSQLBiz {

	public Long findCount(String sql);

	/**
	 * Hibernate 执行原生态SQL语句
	 * 
	 * @param sql
	 */
	public void doSQL(String sql);
	
	public List findBySql(String sql);

}
