package cn.com.fri.basic.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.basic.utils.page.M2MFindByPage;
import cn.com.fri.basic.utils.page.Page;

/**
 * @author W王Y业Q权
 * 
 * @param <T>
 */
@SuppressWarnings("all")
public class BaseHibernateDaoImpl<T> implements IBaseHibernateDao<T> {

	protected Class<T> entityClass;

	protected Logger log = Logger.getLogger(this.getClass().getName());

	protected BaseHibernateDaoImpl() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	/**
	 * @param t
	 */
	@Override
	public void save(T t) {
		this.getSession().save(t);
	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	public T findById(java.io.Serializable id) {
		return (T) getSession().get(this.entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.dao.interfaces.IBaseDao#findAll()
	 */
	@Override
	public List<T> findAll() {
		return this.findAll(null, null, false);
	}

	@Override
	public List<T> findAll(String orderField, String order, boolean cachable) {
		String hql = "from " + this.entityClass.getSimpleName();
		if (StringUtils.isNotBlank(orderField)) {
			if (StringUtils.isBlank(order)) {
				order = "DESC";
			}
			hql += " order by " + orderField + " " + order;
		}
		Query query = getSession().createQuery(hql);
		query.setCacheable(cachable);
		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.dao.interfaces.IBaseDao#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		return this.findByProperty(propertyName, value, null, null, false);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value,
			String orderField, String order) {
		return this.findByProperty(propertyName, value, orderField, order,
				false);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value,
			String field, String order, boolean cachable) {
		String queryString = "from " + entityClass.getSimpleName()
				+ " as model where model." + propertyName + "= ?";
		if (StringUtils.isNotBlank(field)) {
			if (StringUtils.isBlank(order)) {
				order = "DESC";
			}
			queryString += " order by " + field + " " + order;
		}
		Query query = getSession().createQuery(queryString);
		if (value instanceof Date) {
			query.setTimestamp(0, (Date) value);
		} else {
			query.setParameter(0, value);
		}
		query.setCacheable(cachable);
		return query.list();
	}

	/**
	 * @param t
	 * @param excludePropertys
	 *            查询时不作为条件的字段
	 * @return
	 */
	@Override
	public List<T> findByExample(T t, String[] excludePropertys) {
		Example example = Example.create(t);
		if (null != excludePropertys && excludePropertys.length > 0) {
			for (String excludeProperty : excludePropertys) {
				example.excludeProperty(excludeProperty);
			}
		}
		Criteria criteria = this.getSession().createCriteria(this.entityClass);
		criteria.add(example);
		return criteria.list();
	}

	/**
	 * @param groupFieldName
	 * @param orderField
	 * @param order
	 * @param cachable
	 * @return
	 */
	@Override
	public List<Object> findByGroup(String groupFieldName, String orderField,
			String order, boolean cachable) {
		Criteria criteria = getSession().createCriteria(this.entityClass);
		criteria.setProjection(Projections.groupProperty(groupFieldName));
		if (StringUtils.isNotBlank(orderField)) {
			if (StringUtils.isBlank(order)) {
				order = "DESC";
			}
			if (order.toUpperCase().equals("DESC")) {
				criteria.addOrder(Order.desc(orderField));
			} else {
				criteria.addOrder(Order.asc(orderField));
			}
		}
		return criteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#findIn(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public List<T> findIn(String prop, String[] values) {
		String hql = "from " + this.entityClass.getSimpleName() + " where "
				+ prop + " in (:values)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("values", values);
		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.fri.basic.dao.IBaseHibernateDao#findByNotProperty(java.lang.String
	 * , java.lang.Object)
	 */
	public List<T> findByNotProperty(String propertyName, Object value) {
		return this.findByNotProperty(propertyName, value, null, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.fri.basic.dao.IBaseHibernateDao#findByNotProperty(java.lang.String
	 * , java.lang.Object, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public List<T> findByNotProperty(String propertyName, Object value,
			String field, String order, boolean cachable) {
		String queryString = "from " + entityClass.getSimpleName()
				+ " as model where model." + propertyName;
		if (!StringUtils.isNotBlank(field)) {
			if (StringUtils.isBlank(order)) {
				order = "DESC";
			}
			queryString += " order by " + field + " " + order;
		}
		if (value == null) {
			queryString += " is not null";
		} else {
			queryString += " != ?";
		}
		Query query = getSession().createQuery(queryString);
		if (value != null) {
			if (value instanceof Date) {
				query.setTimestamp(0, (Date) value);
			} else {
				query.setParameter(0, value);
			}
		}
		query.setCacheable(cachable);
		return query.list();
	}

	/*
	 * 注：使用createSqlQuery()时Entity中不能有@Formula()
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.fri.basic.dao.IBaseHibernateDao#findPageBySql(java.lang.String)
	 */
	@Override
	public List<T> findListBySql(String sql) {
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(
				this.entityClass);
		List<T> list = query.list();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(T t) {
		getSession().update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#updateIn(java.lang.String,
	 * java.lang.Object, java.lang.String, java.lang.Object[])
	 */
	@Override
	public int updateIn(String setName, Object setValue, String propertyName,
			Object[] values) {
		Session session = getSession();
		String hql = "update " + entityClass.getSimpleName() + " ent set ent."
				+ setName + "=? where ent." + propertyName + " in (:values)";
		Query q = session.createQuery(hql);

		if (setValue instanceof Date) {
			q.setTimestamp(0, (Date) setValue);
		} else {
			q.setParameter(0, setValue);
		}

		q.setParameterList("values", values);

		int result = q.executeUpdate();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#updateAll(java.lang.String,
	 * java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@Override
	public int updateAll(String setName, Object setValue, String propertyName,
			Object value) {
		Session session = getSession();
		String hql = "update " + entityClass.getSimpleName() + " ent set ent."
				+ setName + "=? where ent." + propertyName + " = ?";
		Query q = session.createQuery(hql);
		if (setValue instanceof Date) {
			q.setTimestamp(0, (Date) setValue);
		} else {
			q.setParameter(0, setValue);
		}
		q.setParameter(1, value);
		int result = q.executeUpdate();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#updateAll(java.util.Map,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public int updateAll(Map<String, Object> kv, String prop, Object value) {
		Session session = getSession();
		String hql = "update " + entityClass.getSimpleName() + " ent set ";
		Set<String> keys = kv.keySet();
		for (String key : keys) {
			hql += "ent." + key + " = ?,";
		}
		hql = hql.substring(0, hql.length() - 1);

		hql += " where ent." + prop + " = ?";

		Query q = session.createQuery(hql);
		int i = 0;
		for (String key : keys) {
			q.setParameter(i++, kv.get(key));
		}
		q.setParameter(i, value);
		int result = q.executeUpdate();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#updateAllIn(java.util.Map,
	 * java.lang.String, java.lang.Object[])
	 */
	@Override
	public int updateAllIn(Map<String, Object> kv, String prop, Object[] values) {
		Session session = getSession();
		String hql = "update " + entityClass.getSimpleName() + " ent set ";
		Set<String> keys = kv.keySet();
		for (String key : keys) {
			hql += "ent." + key + " = ?,";
		}
		hql = hql.substring(0, hql.length() - 1);

		hql += " where ent." + prop + " in (:values)";

		Query q = session.createQuery(hql);
		int i = 0;
		for (String key : keys) {
			q.setParameter(i++, kv.get(key));
		}
		q.setParameterList("values", values);
		int result = q.executeUpdate();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(T t) {
		getSession().saveOrUpdate(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		getSession().delete(findById(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#deleteObject(java.lang.Object)
	 */
	@Override
	public void deleteObject(T t) {
		getSession().delete(t);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.fri.basic.dao.IDao.IBaseDao#deleteByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void deleteByProperty(String prop, Object value) {
		Session session = getSession();
		String hql = "delete from " + this.entityClass.getSimpleName()
				+ " where " + prop + "=?";
		Query q = session.createQuery(hql);
		q.setParameter(0, value);
		q.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#deleteIn(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public void deleteIn(String propertyName, Object[] objects) {
		Session session = getSession();
		String hql = "delete from " + this.entityClass.getSimpleName()
				+ " t where t." + propertyName + " in (:values)";
		Query q = session.createQuery(hql);
		q.setParameterList("values", objects);
		int result = q.executeUpdate();
		log.info("批量删除:" + result + "条数据");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.fri.basic.dao.IDao.IBaseDao#exists(java.io.Serializable)
	 */
	@Override
	public boolean exists(Serializable id) {
		return this.findById(id) != null;
	}

	@Override
	public Page<T> findByPageExample(Page<T> page, T t, boolean likeT,
			boolean likePage, String propertyName, String order) {
		Criteria criteria = this.getSession().createCriteria(this.entityClass);
		if (likeT) {
			criteria.add(Example.create(t).ignoreCase().enableLike());
		} else {
			criteria.add(Example.create(t));
		}
		long rowCount = this.initCriteriaParams(page, criteria, likePage);
		if (StringUtils.isNotBlank(propertyName)) {
			if (StringUtils.isBlank(order)) {
				order = "DESC";
			}
			if ("DESC".equals(order.toUpperCase())) {
				criteria.addOrder(Order.desc(propertyName));
			} else {
				criteria.addOrder(Order.asc(propertyName));
			}
		}
		List<T> result = criteria.list();
		page.getData().setTotalCount(rowCount);
		page.getData().setItems(result);
		log.info("分页查询::当前第" + page.getCurrentPage() + "页，每页"
				+ page.getPageSize() + "条，共有数据"
				+ page.getData().getTotalCount() + "条，当前页返回数据条数："
				+ page.getData().getItems().size());
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.fri.basic.dao.IDao.IBaseDao#findByPage(cn.com.fri.basic.utils.
	 * page.Page, java.lang.String, java.lang.String, java.lang.String,
	 * java.sql.Date, java.sql.Date, java.lang.Boolean)
	 */
	@Override
	public Page<T> findByPage(Page<T> page, String propertyName, String order,
			String timeField, Date beginTime, Date endTime, M2MFindByPage m2m,
			Boolean like) {
		Criteria criteria = this.getSession().createCriteria(this.entityClass);

		if (m2m != null) {
			criteria.createAlias(m2m.getAliasName(), m2m.getAliasValue());
			if (m2m.getMainTableQueryParams() != null) {
				Map<String, Object> params = m2m.getMainTableQueryParams();
				Set<String> keys = params.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					criteria.add(Restrictions.eq(key, params.get(key)));
				}
			} else if (!StringUtils.isBlank(m2m.getMainTableFieldName())) {
				criteria.add(Restrictions.eq(m2m.getMainTableFieldName(),
						m2m.getMainTableFieldValue()));
			}

		}

		if (!StringUtils.isBlank(timeField)) {
			if (beginTime != null) // 查询指定时间之后的记录
				criteria.add(Restrictions.ge(timeField, beginTime));
			if (endTime != null) // 查询指定时间之前的记录
				criteria.add(Restrictions.le(timeField, endTime));
		}
		long rowCount = initCriteriaParams(page, criteria, like);

		if (StringUtils.isNotBlank(propertyName)) {
			if (StringUtils.isBlank(order)) {
				order = "DESC";
			}
			if ("DESC".equals(order.toUpperCase())) {
				criteria.addOrder(Order.desc(propertyName));
			} else {
				criteria.addOrder(Order.asc(propertyName));
			}
		}
		List<T> result = criteria.list();
		page.getData().setTotalCount(rowCount);
		page.getData().setItems(result);
		/*
		 * log.info("分页查询::当前第" + page.getCurrentPage() + "页，每页" +
		 * page.getPageSize() + "条，共有数据" + page.getData().getTotalCount() +
		 * "条，当前页返回数据条数：" + page.getData().getItems().size());
		 */
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.fri.basic.dao.IDao.IBaseDao#searchKeyWordByPage(cn.com.fri.basic
	 * .utils.page.Page, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public Page<T> findKeyWordByPage(Page<T> page, String propertyName,
			String order, Boolean like) {
		Criteria criteria = this.getSession().createCriteria(this.entityClass);
		Map<String, Object> map = page.getQueryParams();
		if (map != null && map.size() > 0) {
			Set<String> keys = map.keySet();
			Disjunction disjunction = Restrictions.disjunction();
			for (String key : keys) {
				Object va = map.get(key);
				if (va instanceof Object[]) {
					criteria.add(Restrictions.in(key, (Object[]) va));
					continue;
				} else if (va instanceof Collection) {
					criteria.add(Restrictions.in(key, (Collection<Object>) va));
					continue;
				}
				if (like && !(va instanceof Boolean)) {
					disjunction.add(Restrictions.like(key, "%" + map.get(key)
							+ "%"));
				} else {
					criteria.add(Restrictions.eq(key, map.get(key)));
				}
			}
			criteria.add(disjunction);
		}
		long rowCount = Long.valueOf(criteria
				.setProjection(Projections.rowCount()).uniqueResult()
				.toString());
		criteria.setProjection(null);
		criteria.setFirstResult((page.getCurrentPage() - 1)
				* page.getPageSize());
		criteria.setMaxResults(page.getPageSize());

		if (StringUtils.isNotBlank(propertyName)) {
			if (StringUtils.isBlank(order)) {
				order = "DESC";
			}
			if ("DESC".equals(order.toUpperCase())) {
				criteria.addOrder(Order.desc(propertyName));
			} else {
				criteria.addOrder(Order.asc(propertyName));
			}
		}
		List<T> result = criteria.list();
		page.getData().setTotalCount(rowCount);
		page.getData().setItems(result);
		return page;
	}

	@Override
	public Page<T> findKeyWordByPage_S(Page<T> page, boolean pageLike,
			Map<String, Object> mapIs, Map<String, Object> mapNotIn,
			String timeField, Date beginTime, Date endTime, M2MFindByPage m2m,
			String propertyName, String order) {
		Criteria criteria = this.getSession().createCriteria(this.entityClass);

		if (m2m != null) {
			criteria.createAlias(m2m.getAliasName(), m2m.getAliasValue());
			if (m2m.getMainTableQueryParams() != null) {
				Map<String, Object> params = m2m.getMainTableQueryParams();
				Set<String> keys = params.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					criteria.add(Restrictions.eq(key, params.get(key)));
				}
			} else if (!StringUtils.isBlank(m2m.getMainTableFieldName())) {
				criteria.add(Restrictions.eq(m2m.getMainTableFieldName(),
						m2m.getMainTableFieldValue()));
			}

		}

		if (!StringUtils.isBlank(timeField)) {
			if (beginTime != null) // 查询指定时间之后的记录
				criteria.add(Restrictions.ge(timeField, beginTime));
			if (endTime != null) // 查询指定时间之前的记录
				criteria.add(Restrictions.le(timeField, endTime));
		}

		if (null != mapIs && mapIs.size() > 0) {
			for (String key : mapIs.keySet()) {
				Object va = mapIs.get(key);
				if (va instanceof Object[]) {
					criteria.add(Restrictions.in(key, (Object[]) va));
					continue;
				} else if (va instanceof Collection) {
					criteria.add(Restrictions.in(key, (Collection<Object>) va));
					continue;
				}
				criteria.add(Restrictions.eq(key, mapIs.get(key)));
			}
		}

		if (mapNotIn != null && mapNotIn.size() > 0) {
			for (String key : mapNotIn.keySet()) {
				Object va = mapNotIn.get(key);
				if (va instanceof Object[]) {
					criteria.add(Restrictions.not(Restrictions.in(key,
							(Object[]) va)));
					continue;
				} else if (va instanceof Collection) {
					criteria.add(Restrictions.not(Restrictions.in(key,
							(Collection<Object>) va)));
					continue;
				}
				criteria.add(Restrictions.not(Restrictions.eq(key,
						mapIs.get(key))));
			}
		}

		Map<String, Object> map = page.getQueryParams();
		if (map != null && map.size() > 0) {
			Set<String> keys = map.keySet();
			Disjunction disjunction = Restrictions.disjunction();
			for (String key : keys) {
				Object va = map.get(key);
				if (va instanceof Object[]) {
					criteria.add(Restrictions.in(key, (Object[]) va));
					continue;
				} else if (va instanceof Collection) {
					criteria.add(Restrictions.in(key, (Collection<Object>) va));
					continue;
				}
				if (pageLike) {
					disjunction.add(Restrictions.like(key, "%" + map.get(key)
							+ "%"));
				} else {
					criteria.add(Restrictions.eq(key, map.get(key)));
				}
			}
			criteria.add(disjunction);
		}
		long rowCount = Long.valueOf(criteria
				.setProjection(Projections.rowCount()).uniqueResult()
				.toString());
		criteria.setProjection(null);
		criteria.setFirstResult((page.getCurrentPage() - 1)
				* page.getPageSize());
		criteria.setMaxResults(page.getPageSize());

		if (StringUtils.isNotBlank(propertyName)) {
			if (StringUtils.isBlank(order)) {
				order = "DESC";
			}
			if ("DESC".equals(order.toUpperCase())) {
				criteria.addOrder(Order.desc(propertyName));
			} else {
				criteria.addOrder(Order.asc(propertyName));
			}
		}
		List<T> result = criteria.list();
		page.getData().setTotalCount(rowCount);
		page.getData().setItems(result);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.fri.basic.dao.IDao.IBaseDao#findPageBySql(cn.com.fri.basic.utils
	 * .page.Page, java.lang.String)
	 */
	@Override
	public Page<T> findPageBySql(Page<T> page, String sql) {
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(
				this.entityClass);
		List counts = query.list();
		Long rowCount = (long) counts.size();
		query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		List data = query.list();
		page.getData().setTotalCount(rowCount);
		page.getData().setItems(data);
		return page;
	}

	/**
	 * @param p
	 * @param criteria
	 * @return
	 */
	protected long initCriteriaParams(Page<T> p, Criteria criteria, boolean eq) {
		Map<String, Object> map = p.getQueryParams();
		if (map != null && map.size() > 0) {
			Set<String> keys = map.keySet();
			for (String key : keys) {
				Object va = map.get(key);
				if (va instanceof Object[]) {
					criteria.add(Restrictions.in(key, (Object[]) va));
					continue;
				} else if (va instanceof Collection) {
					criteria.add(Restrictions.in(key, (Collection<Object>) va));
					continue;
				}
				if (eq) {
					criteria.add(Restrictions.like(key, "%" + map.get(key)
							+ "%"));
				} else {
					criteria.add(Restrictions.eq(key, map.get(key)));
				}
			}
		}
		long rowCount = Long.valueOf(criteria
				.setProjection(Projections.rowCount()).uniqueResult()
				.toString());

		criteria.setProjection(null);
		// XXX如果是多对多中间表查询，并且被查询实体中的FetchType是MEARGE，那么需要使用下面这句来过滤掉重复的数据。 //
		// 因为有MEARGE的情况下，会发出多条查询语句，目前不知是否影响性能。需要做进一步的检测。
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setFirstResult((p.getCurrentPage() - 1) * p.getPageSize());
		criteria.setMaxResults(p.getPageSize());
		return rowCount;
	}

}
