package cn.com.fri.basic.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import cn.com.fri.basic.dao.IBaseSQLDao;

@Repository("baseSQLDaoImpl")
public class BaseSQLDaoImpl implements IBaseSQLDao {

	/** 取得日志记录器log */
	protected Logger log = Logger.getLogger(this.getClass().getName());

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Long findCountBySql(String sql) {
		SQLQuery query = getSession().createSQLQuery(sql);
		Long count = Long.valueOf(query.list().get(0).toString());
		return count;
	}

	@Override
	public void doSQL(String sql) {
		SQLQuery query = getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	@Override
	public List findBySql(String sql) {
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}

}
