package cn.com.fri.basic.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import cn.com.fri.basic.utils.page.M2MFindByPage;
import cn.com.fri.basic.utils.page.Page;

/**
 * DAO层接口
 * 
 * @author WYQ
 * 
 * @param <T>
 */
public interface IBaseHibernateDao<T> {

	/**
	 * 获取当前session，是currentSession
	 * 
	 * @return {@link Session}
	 */
	public Session getSession();

	/**
	 * 新增操作
	 * 
	 * @param t
	 */
	public void save(T t);

	/**
	 * 更新数据
	 * 
	 * @param t
	 *            要操作的实体对象
	 */
	public void update(T t);

	/**
	 * 批量更新
	 * 
	 * @param setName
	 *            要更新的字段名称
	 * @param setValue
	 *            更新值
	 * @param propertyName
	 *            查询字段名称
	 * @param values
	 *            查询字段值集合
	 * @return {@link Integer}
	 */
	public int updateIn(String setName, Object setValue, String propertyName,
			Object[] values);

	/**
	 * 更新所有数据
	 * 
	 * @param setName
	 *            要设置的字段名称
	 * @param setValue
	 *            要设置的字段值
	 * @param propertyName
	 *            查询字段名称
	 * @param value
	 *            查询值
	 * @return {@link Integer}
	 */
	public int updateAll(String setName, Object setValue, String propertyName,
			Object value);

	/**
	 * 同时更新多个字段
	 * 
	 * @param kv
	 *            字段与值的集合
	 * @param prop
	 *            条件字段名称
	 * @param value
	 *            条件字段值
	 * @return {@link Integer}
	 */
	public int updateAll(Map<String, Object> kv, String prop, Object value);

	/**
	 * 同时更新多个字段
	 * 
	 * @param kv
	 *            字段与值的集合
	 * @param prop
	 *            条件字段名称
	 * @param value
	 *            条件字段值集合
	 * @return {@link Integer}
	 */
	public int updateAllIn(Map<String, Object> kv, String prop, Object[] value);

	/**
	 * 数据存在时进行更新操作否则SAVE
	 * 
	 * @param t
	 */
	public void saveOrUpdate(T t);

	/**
	 * 根据给定的主键ID删除一条数据
	 * 
	 * @param id
	 *            主键ID
	 */
	public void delete(Serializable id);

	/**
	 * 删除一个对象
	 * 
	 * @param t
	 *            要删除的持久化状态的对象
	 */
	public void deleteObject(T t);

	/**
	 * 根据属性值删除一批数据
	 * 
	 * @param prop
	 *            条件属性名
	 * @param value
	 *            条件属性值
	 */
	public void deleteByProperty(String prop, Object value);

	/**
	 * 批量where in删除
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param objects
	 *            属性值集合
	 * @return {@link Integer}
	 */
	public void deleteIn(String propertyName, Object[] objects);

	/**
	 * 判断一个主键ID在数据库中是否存在
	 * 
	 * @param id
	 *            主键ID
	 * @return {@link Boolean}
	 */
	public boolean exists(Serializable id);

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	public T findById(Serializable id);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public List<T> findAll();

	/**
	 * @param orderField
	 * @param order
	 * @param cachable
	 *            是否可缓存的
	 * @return
	 */
	public List<T> findAll(String orderField, String order, boolean cachable);

	/**
	 * 根据属性查询 (二级缓存关闭)
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value);

	/**
	 * 根据属性查询 (二级缓存关闭)
	 * 
	 * @param propertyName
	 * @param value
	 * @param orderField
	 * @param order
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value,
			String orderField, String order);

	/**
	 * 根据属性进行查询并根据给出的字段进行排序
	 * 
	 * @param propertyName
	 *            要查询的属性名称
	 * @param value
	 *            要查询的属性值
	 * @param field
	 *            要排序的属性字段名称
	 * @param order
	 *            要排序的方式
	 * @param cachable
	 *            是否使用缓存查询
	 * @return {@link List}
	 */
	public List<T> findByProperty(String propertyName, Object value,
			String field, String order, boolean cachable);

	/**
	 * @param t
	 * @return
	 */
	public List<T> findByExample(T t, String[] excludePropertys);

	/**
	 * 分组查询并排序，可设置二级缓存查询。
	 * 
	 * @param groupFieldName
	 *            要分组的字段名称
	 * @param orderField
	 *            排序字段名称
	 * @param order
	 *            排序方式
	 * @param cachable
	 *            是否使用二级缓存
	 * @return {@link List}
	 */
	public List<Object> findByGroup(String groupFieldName, String orderField,
			String order, boolean cachable);

	/**
	 * @param prop
	 *            字段名
	 * @param values
	 *            字段值(一维数组 )
	 * @return LIST 集合
	 */
	public List<T> findIn(String prop, String[] values);

	/**
	 * 查询字段值不等于传入值的数据
	 * 
	 * @param propertyName
	 *            字段名称
	 * @param value
	 *            字段所不等于的值
	 * @return {@link List}
	 */
	public List<T> findByNotProperty(String propertyName, Object value);

	/**
	 * 查询字段值不等于传入值的数据
	 * 
	 * @param propertyName
	 *            字段名称
	 * @param value
	 *            字段所不等于的值
	 * @param field
	 *            要排序的字段
	 * @param order
	 *            排序方式
	 * @param cachable
	 *            是否使用缓存
	 * @return {@link List}
	 */
	public List<T> findByNotProperty(String propertyName, Object value,
			String field, String order, boolean cachable);

	/**
	 * HIBERNATE_CRITERIA_SQL
	 * 
	 * @param sql
	 * @return
	 */
	public List<T> findListBySql(String sql);

	/**
	 * @param page
	 * @param t
	 * @param likeT
	 * @param likePage
	 * @param propertyName
	 * @param order
	 * @return
	 */
	public Page<T> findByPageExample(Page<T> page, T t, boolean likeT,
			boolean likePage, String propertyName, String order);

	/**
	 * 分页查询--带条件(含M2M）
	 * 
	 * @param page
	 * @param propertyName
	 * @param order
	 * @param timeField
	 * @param beginTime
	 * @param endTime
	 * @param m2m
	 * @param like
	 * @return
	 */
	public Page<T> findByPage(Page<T> page, String propertyName, String order,
			String timeField, Date beginTime, Date endTime, M2MFindByPage m2m,
			Boolean like);

	/**
	 * 
	 * 指定实体类中关键字查询
	 * 
	 * @param page
	 * @param propertyName
	 * @param order
	 * @param like
	 * @return
	 */
	public Page<T> findKeyWordByPage(Page<T> page, String propertyName,
			String order, Boolean like);

	/**
	 * 指定实体类中关键字查询2
	 * 
	 * @param page
	 * @param pageLike
	 * @param mapIs
	 *            例(某字段=或in某值/值集合)
	 * @param mapNotIn
	 *            例(某字段！=或！in某值/值集合)
	 * @param propertyName
	 * @param timeField
	 * @param beginTime
	 * @param endTime
	 * @param m2m
	 *            * @param propertyName
	 * @param order
	 * @return
	 */
	public Page<T> findKeyWordByPage_S(Page<T> page, boolean pageLike,
			Map<String, Object> mapIs, Map<String, Object> mapNotIn,
			String timeField, Date beginTime, Date endTime, M2MFindByPage m2m,
			String propertyName, String order);

	/**
	 * SQL语句查询(分页)
	 * 
	 * @param page
	 * @param sql
	 * @return
	 */
	public Page<T> findPageBySql(Page<T> page, String sql);

}
