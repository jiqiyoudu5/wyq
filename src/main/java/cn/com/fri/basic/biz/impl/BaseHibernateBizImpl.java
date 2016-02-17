package cn.com.fri.basic.biz.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.fri.basic.biz.IBaseHibernateBiz;
import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.basic.utils.page.M2MFindByPage;
import cn.com.fri.basic.utils.page.Page;

/**
 * @author WYQ
 * 
 * @param <T>
 */
public abstract class BaseHibernateBizImpl<T> implements IBaseHibernateBiz<T> {

	/** 取得日志记录器log */
	protected Logger log = Logger.getLogger(this.getClass().getName());

	protected IBaseHibernateDao<T> baseDao;

	/**
	 * 设置IBaseDao实体
	 * 
	 * @param baseDao
	 */
	public abstract void setBaseDao(IBaseHibernateDao<T> baseDao);

	@Override
	public void save(T t) {
		baseDao.save(t);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	public int updateIn(String setName, Object setValue, String propertyName,
			Object[] values) {
		return baseDao.updateIn(setName, setValue, propertyName, values);
	}

	@Override
	public int updateAll(String setName, Object setValue, String propertyName,
			Object value) {
		return baseDao.updateAll(setName, setValue, propertyName, value);
	}

	@Override
	public int updateAll(Map<String, Object> kv, String prop, Object value) {
		return baseDao.updateAll(kv, prop, value);
	}

	@Override
	public int updateAllIn(Map<String, Object> kv, String prop, Object[] value) {
		return baseDao.updateAllIn(kv, prop, value);
	}

	@Override
	public void saveOrUpdate(T t) {
		baseDao.saveOrUpdate(t);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	@Override
	public void deleteObject(T t) {
		baseDao.deleteObject(t);
	}

	@Override
	public void deleteByProperty(String prop, Object value) {
		baseDao.deleteByProperty(prop, value);
	}

	@Override
	public void deleteIn(String propertyName, Object[] objects) {
		baseDao.deleteIn(propertyName, objects);
	}

	@Override
	public boolean exists(Serializable id) {
		return baseDao.exists(id);
	}

	@Override
	public T findById(Serializable id) {
		return baseDao.findById(id);
	}

	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}

	@Override
	public List<T> findAll(String orderField, String order, boolean cachable) {
		return baseDao.findAll(orderField, order, cachable);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		return baseDao.findByProperty(propertyName, value);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value,
			String orderField, String order) {
		return baseDao.findByProperty(propertyName, value, orderField, order);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value,
			String field, String order, boolean cachable) {
		return baseDao.findByProperty(propertyName, value, field, order,
				cachable);
	}

	@Override
	public List<T> findByExample(T t, String[] excludePropertys) {
		return baseDao.findByExample(t, excludePropertys);
	}

	@Override
	public List<Object> findByGroup(String groupFieldName, String orderField,
			String order, boolean cachable) {
		return baseDao.findByGroup(groupFieldName, orderField, order, cachable);
	}

	@Override
	public List<T> findIn(String prop, String[] values) {
		return baseDao.findIn(prop, values);
	}

	@Override
	public List<T> findByNotProperty(String propertyName, Object value) {
		return baseDao.findByNotProperty(propertyName, value);
	}

	@Override
	public List<T> findByNotProperty(String propertyName, Object value,
			String field, String order, boolean cachable) {
		return baseDao.findByNotProperty(propertyName, value, field, order,
				cachable);
	}

	@Override
	public List<T> findListBySql(String sql) {
		return baseDao.findListBySql(sql);
	}

	@Override
	public Page<T> findByPageExample(Page<T> page, T t, boolean likeT,
			boolean likePage, String propertyName, String order) {
		return baseDao.findByPageExample(page, t, likeT, likePage,
				propertyName, order);
	}

	@Override
	public Page<T> findByPage(Page<T> page, String propertyName, String order,
			String timeField, Date beginTime, Date endTime, M2MFindByPage m2m,
			Boolean like) {
		return baseDao.findByPage(page, propertyName, order, timeField,
				beginTime, endTime, m2m, like);
	}

	@Override
	public Page<T> findKeyWordByPage(Page<T> page, String propertyName,
			String order, Boolean like) {
		return baseDao.findKeyWordByPage(page, propertyName, order, like);
	}

	@Override
	public Page<T> findKeyWordByPage_S(Page<T> page, boolean pageLike,
			Map<String, Object> mapIs, Map<String, Object> mapNotIn,
			String timeField, Date beginTime, Date endTime, M2MFindByPage m2m,
			String propertyName, String order) {
		return baseDao.findKeyWordByPage_S(page, pageLike, mapIs, mapNotIn,
				timeField, beginTime, endTime, m2m, propertyName, order);
	}

	@Override
	public Page<T> findPageBySql(Page<T> page, String sql) {
		return baseDao.findPageBySql(page, sql);
	}

}
