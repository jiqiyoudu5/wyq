package cn.com.fri.sys.biz;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import cn.com.fri.basic.biz.IBaseHibernateBiz;
import cn.com.fri.sys.dto.TreeDicDto;
import cn.com.fri.sys.po.SYSDictionary;
import cn.com.fri.sys.utils.SYSDictionaryTree;

/**
 * 系统字典业务处接口
 * 
 * @author WYQ
 * 
 */
public interface ISYSDictionaryBiz extends IBaseHibernateBiz<SYSDictionary> {

	/**
	 * Delete父类及其子项字典
	 * 
	 * @param id
	 */
	public void deleteDicType(String[] ids);

	/**
	 * 查询并组装为树
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public SYSDictionaryTree findSYSDictionaryTree()
			throws IllegalAccessException, InvocationTargetException;

	/**
	 * 查询节点下子节点（Ajax）
	 * 
	 * @param parentId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<TreeDicDto> treeListByParentId(String parentId)
			throws IllegalAccessException, InvocationTargetException;

	/**
	 * @param t
	 * @param b
	 *            是否修改所有相关type
	 */
	public void update(SYSDictionary t, boolean b);

}
