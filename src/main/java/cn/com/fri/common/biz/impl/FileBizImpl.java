package cn.com.fri.common.biz.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.com.fri.common.biz.IFileBiz;
import cn.com.fri.sys.biz.ISYSAttachedBiz;
import cn.com.fri.sys.po.SYSAttached;

/**
 * 系统附件操作
 * 
 * @author WYQ
 * 
 */
@Service("fileBizImpl")
public class FileBizImpl implements IFileBiz {

	/** 取得日志记录器log */
	protected Logger log = Logger.getLogger(this.getClass().getName());

	@Autowired
	private ISYSAttachedBiz attachedBiz;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.fri.common.biz.IFileBiz#saveFileUpload(org.springframework.web
	 * .multipart.commons.CommonsMultipartFile, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean saveFileUpload(CommonsMultipartFile file, String postfix,
			String type, String savePath, String newName, String uuidFileName,
			String userName, String relateId) {
		Boolean b = false;
		SYSAttached attached = new SYSAttached();
		try {
			// 写入服务器磁盘
			File localFile = new File(savePath + uuidFileName);
			file.transferTo(localFile);
			// 附件信息入库
			if (StringUtils.isNotBlank(newName)) {
				attached.setName(newName);
			} else {
				attached.setName(file.getOriginalFilename());
			}
			if (StringUtils.isNotBlank(relateId)) {
				attached.setRelateId(relateId);
			}
			attached.setSize(file.getSize());
			attached.setUrl(uuidFileName);
			attached.setPostfix(postfix);
			attached.setType(type);
			attached.setUploadTime(new Date());
			attached.setUploadUser(userName);
			attached.setShzt("2");
			attachedBiz.save(attached);
			b = true;
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * url 目标文件所在路径 fileName 下载到客户端文件名
	 * 
	 * @see cn.com.fri.common.biz.IFileBiz#initDownload(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ResponseEntity<byte[]> initDownload(String url, String fileName)
			throws IOException {
		File file = new File(url);
		byte[] outFile = null;
		if (file.exists()) {
			outFile = FileUtils.readFileToByteArray(file);
		} else {
			fileName = "该文件不存在::" + fileName;
		}
		fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		ResponseEntity<byte[]> re = new ResponseEntity<byte[]>(outFile,
				headers, HttpStatus.OK);
		return re;
	}

	/*
	 * 根据ID删除文件
	 * 
	 * message 0:OK, 1:表中ID不存在,2:被删除对象不存在,3:删除文件出错
	 * 
	 * @see cn.com.fri.common.biz.IFileBiz#deleteFolderById(java.lang.String)
	 */
	@Override
	@Transactional
	public String deleteFolderById(String[] ids, String savePath) {
		String message = "0";
		for (String id : ids) {
			message = deleteFolder(id, null, savePath);
			if (message.equals("0") || message.equals("2")) {
				attachedBiz.delete(id);
			}
		}
		return message;
	}

	/*
	 * 根据URL删除文件
	 * 
	 * message 0:OK, 1:表中ID不存在,2:被删除对象不存在,3:删除文件出错
	 * 
	 * @see cn.com.fri.common.biz.IFileBiz#deleteFolderByUrl(java.lang.String)
	 */
	@Override
	@Transactional
	public String deleteFolderByUrl(String url, String savePath) {
		String message = deleteFolder(null, url, savePath);
		if (message.equals("0") || message.equals("2")) {
			attachedBiz.deleteByProperty("url", url);
		}
		return message;
	}

	/*
	 * message 0:OK, 1:表中ID不存在,2:被删除对象不存在,3:删除文件出错
	 * 
	 * @see cn.com.fri.common.biz.IFileBiz#deleteFile(java.lang.String)
	 */
	@Override
	public String deleteFolder(String id, String url, String savePath) {
		String message = "0";
		if (StringUtils.isNotBlank(id)) {
			SYSAttached t = attachedBiz.findById(id);
			if (null != t && StringUtils.isNotBlank(t.getUrl())) {
				url = savePath + t.getUrl();
			} else {
				message = "1";
			}
		}
		if (StringUtils.isNotBlank(url)) {
			File file = new File(url);
			// 判断目录或文件是否存在
			if (file.exists()) {
				// 判断是否为文件
				if (file.isFile()) {
					message = deleteFile(file);
				} else {
					message = deleteDirectory(file);
				}
			} else {
				message = "2";
			}
		}
		return message;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param url
	 * @return
	 */
	private String deleteFile(File file) {
		String message = "0";
		try {
			file.delete();
		} catch (Exception e) {
			message = "3";
			log.info(e.getMessage());
		}
		return message;
	}

	/**
	 * 删除文件夹及子文件
	 * 
	 * @param file
	 * @param url
	 * @return
	 */
	@Transactional
	private String deleteDirectory(File file) {
		String message = "0";

		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile()) {// 删除子文件
				message = deleteFile(f);
			} else {// 删除子目录
				message = this.deleteDirectory(f);
			}
		}
		// 删除当前目录
		message = deleteFile(file);
		return message;
	}

	@Override
	public String saveClipImg(MultipartFile file, String savePath,
			double maxWidth, double maxHeight, String username)
			throws IOException {
		String tempName = "";
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
			tempName = username + "." + fileExt;
			// 写入服务器磁盘
			File localFile = new File(savePath + "Temp/" + tempName);
			if (localFile.exists()) {
				localFile.delete();
			}
			// 图片尺寸调整
			InputStream inputStream = file.getInputStream();
			BufferedImage image = ImageIO.read(inputStream);
			// 查看宽高是否有超出限额的
			// 宽度不得超过maxWidth
			// 高度不得超过maxHeight
			double w = image.getWidth();
			double h = image.getHeight();
			double percent = 0;
			int nw = 0;
			int nh = 0;
			if (w >= h && w > maxWidth) {
				percent = maxWidth / w;
				this.resizeImage(inputStream, fileExt, localFile, image, w, h,
						percent, nw, nh);
			} else if (h > maxHeight) {
				percent = maxHeight / h;
				this.resizeImage(inputStream, fileExt, localFile, image, w, h,
						percent, nw, nh);
			} else {
				file.transferTo(localFile);
			}
		}
		return tempName;
	}

	/**
	 * 图片压缩函数
	 * 
	 * @param is
	 *            原图片流
	 * @param ext
	 *            原图片后缀
	 * @param outputFile
	 *            将要保存到得文件File
	 * @param image
	 *            原图画布
	 * @param w
	 *            原图片宽
	 * @param h
	 *            原图高
	 * @param percent
	 *            图片压缩比例值
	 * @param nw
	 *            压缩后图片宽
	 * @param nh
	 *            压缩后图片高
	 * @throws IOException
	 */
	private void resizeImage(InputStream is, String ext, File outputFile,
			BufferedImage image, double w, double h, double percent, int nw,
			int nh) throws IOException {
		nw = (int) (w * percent);
		nh = (int) (h * percent);
		BufferedImage im = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_BGR);
		Graphics graphics = im.createGraphics();
		graphics.drawImage(image, 0, 0, nw, nh, null);
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		ImageIO.write(im, ext, outputStream);
		outputStream.flush();
		is.close();
		outputStream.close();
	}

	@Override
	public String saveCropImg(int x, int y, int w, int h, String filePath,
			String savePath, String type, String username) {
		String fileExt = filePath.substring(filePath.lastIndexOf(".") + 1);
		try {
			// 读取将要剪切的图片剪切
			File f1 = new File(savePath + "Temp" + filePath);
			InputStream is = new FileInputStream(f1);
			BufferedImage bufferImage = ImageIO.read(is);
			String newName = UUID.randomUUID().toString().replaceAll("-", "")
					+ "." + fileExt;
			Thumbnails.of(bufferImage).sourceRegion(x, y, w, h).size(w, h)
					.keepAspectRatio(false).toFile(savePath + newName);
			bufferImage.flush();
			is.close();
			// 图片信息入库
			SYSAttached attached = new SYSAttached();
			attached.setRelateId(username);
			attached.setType(type);
			String[] st = { "size" };
			List<SYSAttached> att = attachedBiz.findByExample(attached, st);
			if (null != att && att.size() > 0) {
				for (SYSAttached t : att) {
					String url = savePath + t.getUrl();
					this.deleteFolder(null, url, null);
					attachedBiz.delete(t.getId());
				}
			}
			File nf = new File(savePath + newName);
			attached.setSize(nf.length());
			attached.setName(username + "." + fileExt);
			attached.setUrl(newName);
			attached.setPostfix(fileExt);
			attached.setType(type);
			attached.setRelateId(username);
			attached.setUploadTime(new Date());
			attached.setUploadUser(username);
			attached.setShzt("1");
			attachedBiz.save(attached);
			return newName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
