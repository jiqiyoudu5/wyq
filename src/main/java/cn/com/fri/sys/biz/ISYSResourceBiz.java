package cn.com.fri.sys.biz;

import java.util.List;
import java.util.Set;

import cn.com.fri.basic.biz.IBaseHibernateBiz;
import cn.com.fri.sys.po.SYSResource;
import cn.com.fri.sys.po.SYSRole;

public interface ISYSResourceBiz extends IBaseHibernateBiz<SYSResource> {

	public List<String> findAllTypes();

	/**
	 * DAO中设定了抓取策略(FetchMode.JOIN), 等于动态指定实体类中的fetch = FetchType.MERGE设置
	 * 
	 * @param resid
	 * @return
	 */
	public Set<SYSRole> getRolesByResourceId(String resid);

}
