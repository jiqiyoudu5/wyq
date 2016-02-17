package cn.com.fri.sys.biz;

import java.io.IOException;

public interface ISYSDatabaseBackAndRestoreBiz {

	/**
	 * cmd mysqldump 备份并直接存入磁盘文件
	 * 
	 * @param dbPath
	 *            备份文件所在路径
	 * @param dbFileName
	 *            自定义备份文件名
	 * @param sqlBinPath
	 *            SQL安装Bin根目录
	 * @param dbIP
	 *            数据库IP
	 * @param dbPort
	 *            数据库端口
	 * @param dbuser
	 *            数据库用户名
	 * @param dbpassword
	 *            数据库密码
	 * @param dbName
	 *            数据库名称
	 * @param dbName
	 *            操作人
	 * @return
	 */
	public boolean backdb(String dbPaht, String dbFileName, String sqlBinPath,
			String dbIP, String dbPort, String dbuser, String dbpassword,
			String dbName, String lineUsername);

	/**
	 * cmd mysqldump 读取字节流存入磁盘文件
	 * 
	 * @param dbPath
	 *            备份文件所在路径
	 * @param dbFileName
	 *            自定义备份文件名
	 * @param sqlBinPath
	 *            SQL安装Bin根目录
	 * @param dbIP
	 *            数据库IP
	 * @param dbPort
	 *            数据库端口
	 * @param dbuser
	 *            数据库用户名
	 * @param dbpassword
	 *            数据库密码
	 * @param dbName
	 *            数据库名称
	 * @param dbName
	 *            操作人
	 * @throws IOException
	 */
	public void backdb_IO(String dbPath, String dbFileName, String sqlBinPath,
			String dbIP, String dbPort, String dbuser, String dbpassword,
			String dbName, String lineUsername) throws IOException;

}
