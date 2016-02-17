package cn.com.fri.sys.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.fri.basic.dao.impl.BaseHibernateDaoImpl;
import cn.com.fri.sys.dao.ISYSDictionaryDao;
import cn.com.fri.sys.po.SYSDictionary;

@Repository("sysDictionaryDaoImpl")
public class SYSDictionaryDaoImpl extends BaseHibernateDaoImpl<SYSDictionary>
		implements ISYSDictionaryDao {

}
