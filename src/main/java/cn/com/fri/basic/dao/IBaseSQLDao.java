package cn.com.fri.basic.dao;

import java.util.List;

import org.hibernate.Session;

/**
 * @author WYQ
 * 
 */
public interface IBaseSQLDao {

	/**
	 * 获取当前session，是currentSession
	 * 
	 * @return {@link Session}
	 */
	public Session getSession();

	/**
	 * SQL语句统计查询
	 * 
	 * @param sql
	 * @return
	 */
	public Long findCountBySql(String sql);

	/**
	 * Hibernate 执行原生态SQL语句
	 * 
	 * @param sql
	 */
	public void doSQL(String sql);
	
	public List findBySql(String sql);

}
