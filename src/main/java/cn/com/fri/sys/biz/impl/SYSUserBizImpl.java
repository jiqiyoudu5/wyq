package cn.com.fri.sys.biz.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.fri.basic.biz.impl.BaseHibernateBizImpl;
import cn.com.fri.basic.dao.IBaseHibernateDao;
import cn.com.fri.sys.biz.ISYSRoleBiz;
import cn.com.fri.sys.biz.ISYSUserBiz;
import cn.com.fri.sys.dao.ISYSUserDao;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;

@Service("sysUserBizImpl")
public class SYSUserBizImpl extends BaseHibernateBizImpl<SYSUser> implements
		ISYSUserBiz {

	@Autowired
	public void setBaseDao(IBaseHibernateDao<SYSUser> sysUserDaoImpl) {
		super.baseDao = sysUserDaoImpl;
	}

	@Autowired
	private ISYSUserDao dao;

	@Autowired
	private ISYSRoleBiz roleBiz;

	@Override
	@Transactional
	public Set<SYSRole> findUserRolesByUsername(String username) {
		return dao.getRoles(username);
	}

	@Override
	@Transactional
	public void removeVipPower(String[] roles, String username) {
		SYSUser user = dao.findById(username);
		for (String roleId : roles) {
			SYSRole vipRole = roleBiz.findById(roleId);
			user.getRoles().remove(vipRole);
		}
		user.setVip(false);
	}

	@Override
	public void save(SYSUser t) {
		MessageDigestPasswordEncoder me = new MessageDigestPasswordEncoder(
				"MD5");
		me.setEncodeHashAsBase64(false);
		t.setPassword(me.encodePassword(t.getPassword().trim(), t.getUsername()
				.trim()));
		this.baseDao.save(t);
	}

}
