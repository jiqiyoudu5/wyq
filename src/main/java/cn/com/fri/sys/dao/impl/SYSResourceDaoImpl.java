package cn.com.fri.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.fri.basic.dao.impl.BaseHibernateDaoImpl;
import cn.com.fri.sys.dao.ISYSResourceDao;
import cn.com.fri.sys.po.SYSResource;
import cn.com.fri.sys.po.SYSRole;

@Repository("sysResourceDaoImpl")
public class SYSResourceDaoImpl extends BaseHibernateDaoImpl<SYSResource>
		implements ISYSResourceDao {

	@Override
	public List<String> getAllTypes() {
		List<Object> ress = this.findByGroup("type", "type", "desc", true);
		List<String> typeNames = new ArrayList<String>();
		for (Object res : ress) {
			typeNames.add(res.toString());
		}
		return typeNames;
	}

	@Override
	public Set<SYSRole> getRoles(String resid) {
		Criteria c = this.getSession().createCriteria(SYSResource.class);
		c.add(Restrictions.eq("id", resid));
		c.setFetchMode("roles", FetchMode.JOIN);
		SYSResource r = (SYSResource) c.uniqueResult();
		log.info(r.toString());
		return r.getRoles();
	}
}
