package cn.com.fri.common.biz;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 系统附件操作
 * 
 * @author WYQ
 * 
 */
public interface IFileBiz {

	/**
	 * @param file
	 * @param postfix
	 *            文件后缀名
	 * @param type
	 *            文件所属分类
	 * @param savePath
	 * @param newName
	 *            文件重名名值
	 * @param uuidFileName
	 *            文件重命名值
	 * @param userName
	 * @param relateId
	 *            关联信息ID
	 * @return
	 */
	public Boolean saveFileUpload(CommonsMultipartFile file, String postfix,
			String type, String savePath, String newName, String uuidFileName,
			String userName, String relateId);

	/**
	 * @param url
	 *            目标文件所在磁盘路径
	 * @param fileName
	 *            下载到客户端文件名
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<byte[]> initDownload(String url, String fileName)
			throws IOException;

	/**
	 * 根据ID删除文件
	 * 
	 * @param ids
	 * 
	 * @param savePath
	 * 
	 * @return 0:OK, 1:表中ID不存在,2:被删除对象不存在,3:删除文件出错
	 */
	public String deleteFolderById(String[] ids, String savePath);

	/**
	 * 根据URL删除文件
	 * 
	 * @param url
	 * 
	 * @param savePath
	 * 
	 * @return 0:OK, 1:表中ID不存在,2:被删除对象不存在,3:删除文件出错
	 */
	public String deleteFolderByUrl(String url, String savePath);

	/**
	 * 
	 * 删除文件或文件夹及子文件
	 * 
	 * @param id
	 *            库表中文件档案ID
	 * @param url
	 *            文件所在路徑
	 * 
	 * @param savePath
	 * 
	 * @return 0:OK, 1:表中ID不存在,2:被删除对象不存在,3:删除文件出错
	 */
	public String deleteFolder(String id, String url, String savePath);

	/**
	 * 上传图片并限制宽高等比例压缩图片
	 * 
	 * @param file
	 *            上传的文件
	 * @param savePath
	 *            保存的路径
	 * @param maxWidth
	 *            最大宽
	 * @param maxHeight
	 *            最大高
	 * @param username
	 *            当前用户名
	 * @return
	 * @throws IOException
	 */
	public String saveClipImg(MultipartFile file, String savePath,
			double maxWidth, double maxHeight, String username)
			throws IOException;

	/**
	 * @param x
	 *            坐标X轴值
	 * @param y
	 *            坐标Y轴值
	 * @param w
	 *            坐标(X,Y)X轴延伸值
	 * @param h
	 *            坐标(X,Y)Y轴延伸值
	 * @param filePath
	 *            被剪切图片路径
	 * @param savePath
	 *            新图片保存路径
	 * @param type
	 *            图片在系统中分类值
	 * @param username
	 *            当前操作用户
	 * @return String newName 剪切后新图片名
	 */
	public String saveCropImg(int x, int y, int w, int h, String filePath,
			String savePath, String type, String username);

}
