package cn.com.fri.sys.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.fri.basic.biz.impl.BaseHibernateBizImpl;
import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.sys.biz.ISYSDictionaryBiz;
import cn.com.fri.sys.dao.ISYSDictionaryDao;
import cn.com.fri.sys.dto.TreeDicDto;
import cn.com.fri.sys.po.SYSDictionary;
import cn.com.fri.sys.utils.SYSDictionaryTree;
import cn.com.fri.sys.utils.TreeUtil;

@Service("sysDictionary")
public class SYSDictionaryBizImpl extends BaseHibernateBizImpl<SYSDictionary>
		implements ISYSDictionaryBiz {

	@Autowired
	private ISYSDictionaryDao dao;

	@Override
	@Autowired
	public void setBaseDao(IBaseHibernateDao<SYSDictionary> sysDictionaryDaoImpl) {
		super.baseDao = sysDictionaryDaoImpl;
	}

	@Override
	@Transactional
	public void update(SYSDictionary t, boolean b) {
		super.baseDao.update(t);
		if (b) {
			super.baseDao.updateAll("type", t.getType(), "parent", t.getId());
		}
	}

	@Override
	@Transactional
	public void deleteDicType(String[] ids) {
		super.baseDao.deleteIn("id", ids);
		super.baseDao.deleteIn("parent", ids);
	}

	@Override
	@Transactional
	public SYSDictionaryTree findSYSDictionaryTree()
			throws IllegalAccessException, InvocationTargetException {
		SYSDictionary root = dao.findById("wyq8d8i8c8r8o8o8t");
		if (root != null) {
			SYSDictionaryTree tree = new SYSDictionaryTree();
			BeanUtils.copyProperties(root, tree);
			tree = findChildDic(tree);
			return tree;
		}
		return null;
	}

	private SYSDictionaryTree findChildDic(SYSDictionaryTree tree)
			throws IllegalAccessException, InvocationTargetException {
		List<SYSDictionary> dics = dao.findByProperty("parent", tree.getId());
		if (dics == null || dics.size() == 0) {
			return tree;
		}
		for (SYSDictionary dic : dics) {
			SYSDictionaryTree d = new SYSDictionaryTree();
			BeanUtils.copyProperties(dic, d);
			d = this.findChildDic(d);
			tree.getChildren().add(d);
		}
		return tree;

	}

	@Override
	@Transactional
	public List<TreeDicDto> treeListByParentId(String parentId)
			throws IllegalAccessException, InvocationTargetException {
		List<SYSDictionary> root = this.baseDao.findByProperty("parent",
				parentId);
		List<TreeDicDto> t = new ArrayList<TreeDicDto>();
		if (null != root && root.size() > 0) {
			for (SYSDictionary d : root) {
				TreeDicDto rd = new TreeDicDto();
				TreeUtil.convertToTree(rd, d);
				List<SYSDictionary> dic = this.baseDao.findByProperty("parent",
						rd.getId());
				if (dic == null || dic.size() == 0) {
					rd.setLeaf(true);
				} else {
					rd.setLeaf(false);
				}
				rd.setExpanded(false);
				t.add(rd);
			}
		}
		return t;
	}

}
