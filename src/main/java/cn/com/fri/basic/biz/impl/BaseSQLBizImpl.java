package cn.com.fri.basic.biz.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.fri.basic.biz.IBaseSQLBiz;
import cn.com.fri.basic.dao.IBaseSQLDao;

/**
 * @author WYQ
 * 
 */
@Service("baseSQLBizImpl")
public class BaseSQLBizImpl implements IBaseSQLBiz {

	/** 取得日志记录器log */
	protected Logger log = Logger.getLogger(this.getClass().getName());

	@Autowired
	private IBaseSQLDao dao;

	@Override
	public Long findCount(String sql) {
		return dao.findCountBySql(sql);
	}

	@Override
	public void doSQL(String sql) {
		dao.doSQL(sql);
	}

	@Override
	public List findBySql(String sql) {
		// TODO Auto-generated method stub
		return dao.findBySql(sql);
	}

}
