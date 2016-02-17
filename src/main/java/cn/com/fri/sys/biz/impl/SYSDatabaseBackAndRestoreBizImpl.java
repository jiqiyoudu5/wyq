package cn.com.fri.sys.biz.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.fri.sys.biz.ISYSAttachedBiz;
import cn.com.fri.sys.biz.ISYSDatabaseBackAndRestoreBiz;
import cn.com.fri.sys.po.SYSAttached;

@Service("sysDatabaseBackAndRestoreBizImpl")
public class SYSDatabaseBackAndRestoreBizImpl implements
		ISYSDatabaseBackAndRestoreBiz {

	@Autowired
	private ISYSAttachedBiz attachedBiz;

	@Override
	public boolean backdb(String dbPath, String dbFileName, String sqlBinPath,
			String dbIP, String dbPort, String dbuser, String dbpassword,
			String dbName, String lineUsername) {
		// 创建文件
		String fileName = "";
		if (StringUtils.isNotBlank(dbFileName)) {
			fileName = dbFileName;
		} else {
			fileName = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(new Date()) + ".sql";
		}
		File f = new File(dbPath + fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String cmdString = "cmd.exe /c " + sqlBinPath + " mysqldump -h " + dbIP
				+ " -p" + dbPort + " -u " + dbuser + " -p" + dbpassword + " "
				+ dbName + " --set-charset=utf-8 --result-file=" + dbPath
				+ fileName;
		try {
			Process process = Runtime.getRuntime().exec(cmdString);
			int tag = process.waitFor();// 等待进程终止
			if (tag == 0) {
				savedbbackFileInfo(dbPath, fileName, dbName, lineUsername);
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void backdb_IO(String dbPath, String dbFileName, String sqlBinPath,
			String dbIP, String dbPort, String dbuser, String dbpassword,
			String dbName, String lineUsername) throws IOException {
		String cmdString = "cmd.exe /c " + sqlBinPath + " mysqldump -h " + dbIP
				+ " -p" + dbPort + " -u " + dbuser + " -p" + dbpassword + " "
				+ dbName + " --set-charset=utf-8 ";
		Process process = Runtime.getRuntime().exec(cmdString);

		String fileName = "";
		if (StringUtils.isNotBlank(dbFileName)) {
			fileName = dbFileName;
		} else {
			fileName = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(new Date()) + ".sql";
		}
		// 构造输出流对象文件（创建一个将被写入内容的文件）
		FileOutputStream os = new FileOutputStream(dbPath + fileName);
		// 构造上传文件的输入流（获取将要写入的内容）
		InputStreamReader inputStreamReader = new InputStreamReader(
				process.getInputStream(), "utf-8");
		BufferedReader in = new BufferedReader(inputStreamReader);
		// 以写字节的方式写文件
		int b = 0;
		while ((b = in.read()) != -1) {
			os.write(b);
		}
		// 清空并关闭输入输出流
		os.flush();
		os.close();
		in.close();

		savedbbackFileInfo(dbPath, fileName, dbName, lineUsername);
	}

	public void savedbbackFileInfo(String dbPath, String fileName,
			String dbName, String lineUsername) {
		SYSAttached att = new SYSAttached();
		att.setShzt("1");
		att.setUploadTime(new Date());
		att.setName(dbName + ".sql");
		att.setPostfix("sql");
		att.setType("402883e04f058f54014f059f4e850000");// 数据备份
		att.setUrl(fileName);
		att.setUploadUser(lineUsername);

		File fsize = new File(dbPath + fileName);
		if (fsize.exists() && fsize.isFile()) {
			att.setSize(fsize.length());
		}
		attachedBiz.save(att);
	}

	/**
	 * 获取指定文件大小返回字符串
	 * 
	 * @param path
	 * @return
	 */
	private String initFileSize(String path) {
		// fileSize
		String size = "";
		File fsize = new File(path);
		if (fsize.exists() && fsize.isFile()) {
			long v = fsize.length();
			if (0 < v && v < 1024) {
				size = Math.round(v * 100) / 100 + " Byte";
			} else if (1024 <= v && v < Math.pow(1024, 2)) {
				size = Math.round(v * 100 / 1024) / 100 + " KB";
			} else if (Math.pow(1024, 2) <= v && v < Math.pow(1024, 3)) {
				size = Math.round(v * 100 / Math.pow(1024, 2)) / 100 + " MB";
			} else if (Math.pow(1024, 3) <= v && v < Math.pow(1024, 4)) {
				size = Math.round(v * 100 / Math.pow(1024, 3)) / 100 + " GB";
			} else {
				size = Math.round(v * 100 / Math.pow(1024, 4)) / 100 + " TB";
			}
		}
		return size;
	}
}
