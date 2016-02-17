package cn.com.fri.sys.utils;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import cn.com.fri.basic.biz.IBaseSQLBiz;
import cn.com.fri.spring.security.customFilters.MySecurityMetadataSource;
import cn.com.fri.sys.biz.ISYSDictionaryBiz;
import cn.com.fri.sys.biz.ISYSMenuBiz;
import cn.com.fri.sys.biz.ISYSResourceBiz;
import cn.com.fri.sys.biz.ISYSRoleBiz;
import cn.com.fri.sys.biz.ISYSUserBiz;
import cn.com.fri.sys.po.SYSDictionary;
import cn.com.fri.sys.po.SYSMenu;
import cn.com.fri.sys.po.SYSResource;
import cn.com.fri.sys.po.SYSRole;
import cn.com.fri.sys.po.SYSUser;

@Repository
public class InitSystem implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(InitSystem.class);

	@Autowired
	private ISYSUserBiz userBiz;

	@Autowired
	private ISYSMenuBiz menuBiz;

	@Autowired
	private IBaseSQLBiz sqlBiz;

	@Autowired
	private ISYSResourceBiz resourceBiz;

	@Autowired
	private ISYSRoleBiz roleBiz;

	@Autowired
	private ISYSDictionaryBiz dicBiz;

	@Autowired
	@Qualifier("mySecurityMetadataSource")
	private MySecurityMetadataSource sms;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// 加判断防止执行两次，因为系统存在两个容器，一个是root application，另一个是
		// application-servlet.xml(root application子容器)
		// 下面判断还不行时用:
		// arg0.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")
		// 判断
		if (arg0.getApplicationContext().getParent() == null) {
			log.info("初始化系统常量...");
			this.initSystemStaticVariables();
			log.info("初始化系统菜单...");
			this.initSYSMenu();
			log.info("初始化系统资源...");
			this.initSYSResource();
			log.info("初始化系统角色...");
			this.initRole();
			log.info("初始化系统字典...");
			this.initDictionary();
			log.info("初始化系统管理员账号...");
			this.initRootUser();
		}
	}

	private void initSystemStaticVariables() {

		SYSInitValues.SYSDBIP = SYSPropertiesUtil.getValue("sys.db.ip");
		SYSInitValues.SYSDBPORT = SYSPropertiesUtil.getValue("sys.db.port");
		SYSInitValues.SYSDBSQLBINPATH = SYSPropertiesUtil
				.getValue("sys.db.sqlBinPath");
		SYSInitValues.SYSDBNAME = SYSPropertiesUtil.getValue("sys.db.name");
		SYSInitValues.SYSDBUSER = SYSPropertiesUtil.getValue("sys.db.user");
		SYSInitValues.SYSDBPASSWORD = SYSPropertiesUtil
				.getValue("sys.db.password");

		SYSInitValues.ROOTUSERNAME = SYSPropertiesUtil
				.getValue("sys.user.rootusername");
		SYSInitValues.ROOTPASSWORD = SYSPropertiesUtil
				.getValue("sys.user.rootpassword");
		SYSInitValues.SYSMENUROOTID = SYSPropertiesUtil
				.getValue("sys.menu.rootid");
		SYSInitValues.SYSMENUROOT2TEXT = SYSPropertiesUtil
				.getValue("sys.menu.root2text");
		SYSInitValues.SYSRESOURCEID = SYSPropertiesUtil
				.getValue("sys.resource.id");
		SYSInitValues.SYSROLEID = SYSPropertiesUtil.getValue("sys.role.id");
		SYSInitValues.SYSDICTIONARYID = SYSPropertiesUtil
				.getValue("sys.dictionary.rootid");
		SYSInitValues.FILETYPE = SYSPropertiesUtil.getValue("sys.file.type")
				.toLowerCase();
		SYSInitValues.MAXUPLOADSIZE = Long.parseLong(SYSPropertiesUtil
				.getValue("sys.file.maxUploadSize"));

		SYSInitValues.SAVEPATH = SYSPropertiesUtil
				.getValue("sys.path.savePaht");
		File file = new File(SYSInitValues.SAVEPATH + "Temp/");
		if (!file.exists()) {
			file.mkdirs();
		}
		log.info("初始化系统常量结束");
	}

	private void initSYSMenu() {
		SYSMenu menu = menuBiz.findById(SYSInitValues.SYSMENUROOTID);
		if (null == menu) {
			String sql = "insert  into sys_menu";
			sql += "(id,text,pid,icon,value,expanded,leaf,viewType,orderValue,remark) values ";
			sql += "('297e83e043986bcb014398903c6d0001','您没有菜单可以使用！','402883e043330ab50143330ab8920001','','',0,0,'','99999999','当清除用户菜单时，无菜单项提示用。'),"
					+ "('"
					+ SYSInitValues.SYSMENUROOTID
					+ "','根','','','',1,0,'','001','根'),"
					+ "('402883e04332f897014333107d030001','用户管理','402883e043330dd20143330dd6d90001','','sysUserList',0,1,'','102','用户的新增、修改、删除等操作'),"
					+ "('402883e04332f8970143331276140003','资源管理','402883e043330dd20143330dd6d90001','','sysResourceList',0,1,'','103','系统资源的新增、修改、删除等操作'),"
					+ "('402883e04332f89701433312f96d0004','角色管理','402883e043330dd20143330dd6d90001','','sysRoleMain',0,1,'','105','用户权限管理'),"
					+ "('402883e043330ab50143330ab8920001','"
					+ SYSInitValues.SYSMENUROOT2TEXT
					+ "','"
					+ SYSInitValues.SYSMENUROOTID
					+ "','','',1,0,'','002','此节点不能删除'),"
					+ "('402883e043330dd20143330dd6d90001','用户与权限管理','402883e043330ab50143330ab8920001','','',1,0,'','100',''),"
					+ "('402883e043330f5d0143330f60670001','菜单管理','402883e043330dd20143330dd6d90001','','sysTreeList',0,1,'','104','系统菜单的新增、修改、删除等操作'),"
					+ "('402883e0436f525d01436f539e970001','系统字典管理','402883e043330ab50143330ab8920001','','',1,0,'','200',''),"
					+ "('402883e04936e0b0014936ea44ec0000','字典项管理','402883e0436f525d01436f539e970001','','sysDictionaryMain',1,1,'','201','系统菜单项管理'),"
					+ "('402883e04a0ddf89014a0dea35e30000','文字与附件管理','402883e043330ab50143330ab8920001','','',1,0,'','300','网站文章、信息、附件管理'),"
					+ "('402883e04a0ddf89014a0defbbb70001','文字管理','402883e04a0ddf89014a0dea35e30000','','wenzi',1,0,'','01','文字、文章管理'),"
					+ "('402883e04a0ddf89014a0df16f5e0002','附件管理','402883e04a0ddf89014a0dea35e30000','','fj',0,1,'','02','上传、下载的文档、图片视频管理');";
			try {
				sqlBiz.doSQL(sql);
				log.info("初始化系统菜单完成");
			} catch (Exception e) {
				log.error("初始化系统菜单出错::" + e.getMessage());
			}
		} else {
			log.info("系统初始菜单已存在，跳过。");
		}
	}

	private void initSYSResource() {
		SYSResource resource = resourceBiz
				.findById(SYSInitValues.SYSRESOURCEID);
		if (null == resource) {
			String sql = "insert into sys_resource (id, url, name, endreguser, endupdatetime, remark) values ";
			sql += "('40280900429860c301429860c6370001','/user/home.jsp','用户后台首页','root','2014-09-11 09:08:16','普通用户'),"
					+ "('402883e0427e636e01427e6371a70001','/sys/home.jsp','管理员后台首页','root','2014-09-11 09:21:56','管理员专属'),"
					+ "('"
					+ SYSInitValues.SYSRESOURCEID
					+ "','/sys/*/*','SYS操作','root','2014-09-10 16:50:27','系统配置操作'),"
					+ "('402883e0485ea20501485ea5afd80000','/sys/menu/byuserline','菜单显示','root','2014-09-10 16:19:49','根据用户名加载菜单'),"
					+ "('402883e04a278ac3014a278c8f710000','/user/viphome.jsp','VIP后台首页','root','2014-12-08 09:38:50','VIP会员后台管理');";
			try {
				sqlBiz.doSQL(sql);
				log.info("初始化系统资源完成");
			} catch (Exception e) {
				log.error("初始化系统资源出错::" + e.getMessage());
			}
		} else {
			log.info("系统初始资源已存在，跳过。");
		}
	}

	private void initRole() {
		SYSRole role = roleBiz.findById(SYSInitValues.SYSROLEID);
		if (null == role) {
			String sql = "insert  into sys_role(id,code,name,remark) values ";
			sql += "('297e83e0454e660f01454e743bda0001','ROLE_USER','普通管理员','普通管理员角色组'),"
					+ "('"
					+ SYSInitValues.SYSROLEID
					+ "','ROLE_ADMIN','管理员','管理员用户组'),"
					+ "('402883e0490d35b601490d362adb0000','ROLE_VIP','VIP用户','VIP用户(ROLE_VIP)');";
			String sql_role_menu = "insert into sys_role_menu (roleid, menuid) values ";
			sql_role_menu += "('402883e0427dcc3301427dcc36a00001','402883e04332f8970143331276140003'),('402883e0427dcc3301427dcc36a00001','402883e04332f89701433312f96d0004'),('402883e0427dcc3301427dcc36a00001','402883e043330f5d0143330f60670001'),('402883e0427dcc3301427dcc36a00001','402883e043330ab50143330ab8920001'),('402883e0427dcc3301427dcc36a00001','402883e043330dd20143330dd6d90001'),('402883e0427dcc3301427dcc36a00001','402883e0436f525d01436f539e970001'),('402883e0427dcc3301427dcc36a00001','402883e04332f897014333107d030001'),('297e83e0454e660f01454e743bda0001','402883e043330ab50143330ab8920001'),('402883e0427dcc3301427dcc36a00001','402883e04936e0b0014936ea44ec0000'),('402883e0427dcc3301427dcc36a00001','402883e04a0ddf89014a0defbbb70001'),('402883e0427dcc3301427dcc36a00001','402883e04a0ddf89014a0df16f5e0002'),('402883e0427dcc3301427dcc36a00001','402883e04a0ddf89014a0dea35e30000'),('402883e0490d35b601490d362adb0000','297e83e043986bcb014398903c6d0001'),('297e83e0454e660f01454e743bda0001','297e83e043986bcb014398903c6d0001');";
			String sql_role_resource = "insert into sys_role_resource (roleid, resourceid) values";
			sql_role_resource = "('402883e0427dcc3301427dcc36a00001','402883e0427e636e01427e6371a70001'),('402883e0427dcc3301427dcc36a00001','402883e0485ea20501485ea5afd80000'),('297e83e0454e660f01454e743bda0001','402883e0485ea20501485ea5afd80000'),('402883e0427dcc3301427dcc36a00001','402883e0485e686501485e7cc51d0000'),('297e83e0454e660f01454e743bda0001','40280900429860c301429860c6370001'),('402883e0490d35b601490d362adb0000','402883e04a278ac3014a278c8f710000'),('402883e0490d35b601490d362adb0000','402883e0485ea20501485ea5afd80000');";
			try {
				sqlBiz.doSQL(sql);
				sqlBiz.doSQL(sql_role_menu);
				sqlBiz.doSQL(sql_role_resource);
				log.info("系统初始化角色完成。");
			} catch (Exception e) {
				log.error("系统初始化角色出错::" + e.getMessage());
			}
		} else {
			log.info("系统角色已存在，跳过。");
		}
	}

	private void initDictionary() {
		SYSDictionary dic = dicBiz.findById(SYSInitValues.SYSDICTIONARYID);
		if (null == dic) {
			String sql = "insert into sys_dictionary (id, type, parent, text, remark, ordervalue) values ";
			sql += "('wyq8d8i8c8t8y8p8e','DICTYPE','root','字典根节点','','2');";
			try {
				sqlBiz.doSQL(sql);
				log.info("初始化系统字典完成。");
			} catch (Exception e) {
				log.error("初始化系统字典出错::" + e.getMessage());
			}
		} else {
			log.info("系统字典已存在，跳过。");
		}
	}

	private void initRootUser() {
		SYSUser user = userBiz.findById(SYSInitValues.ROOTUSERNAME);
		if (null == user) {
			user = new SYSUser();
			user.setEnabled(true);
			user.setLocked(false);
			user.setBusinessName("WYQ");
			user.setPhone("010");
			user.setTel("010");
			user.setAddress("010");
			user.setDistrict("110000");
			user.setVip(true);
			user.setPassword(SYSInitValues.ROOTPASSWORD);
			user.setRegTime(new Date());
			user.setUsername(SYSInitValues.ROOTUSERNAME);
			try {
				userBiz.save(user);
				sqlBiz.doSQL("insert into sys_user_role (roleid, username) values('402883e0427dcc3301427dcc36a00001','"
						+ SYSInitValues.ROOTUSERNAME + "');");
				sms.refresh();
				log.info("初始化系统系统管理员账号结束");
			} catch (Exception e) {
				log.error("系统初始超级管理员时出错::" + e.getMessage());
			}
		} else {
			log.info("系统管理员账号已存在，跳过。");
		}
	}

}
