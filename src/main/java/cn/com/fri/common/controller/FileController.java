package cn.com.fri.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.com.fri.basic.controller.BaseController;
import cn.com.fri.common.biz.IFileBiz;
import cn.com.fri.sys.biz.ISYSAttachedBiz;
import cn.com.fri.sys.po.SYSAttached;
import cn.com.fri.sys.utils.SYSInitValues;

/**
 * 1.系统文件upload & download
 * 
 * 2.测试： 不同方法上传效率测试
 * 
 * 3.文件下载
 * 
 * 4.读取图片
 * 
 * 5.刪除文件
 * 
 * @author WYQ
 * 
 */
@Controller
@RequestMapping("/sys/file")
public class FileController extends BaseController {

	@Autowired
	private ISYSAttachedBiz biz;

	@Autowired
	private IFileBiz fileBiz;

	// 系统附件地址常量
	private String savePath = SYSInitValues.SAVEPATH;
	// 可上传的文件格式
	private String fileType_ = SYSInitValues.FILETYPE;
	// 一次请求上传文件最大值
	private long maxUploadSize = SYSInitValues.MAXUPLOADSIZE;
	// 转为兆
	private long maxUploadSize_M = maxUploadSize / 1048576;

	/**
	 * 文件上传（所有格式）
	 * 
	 * @param files
	 * @param type
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	public ModelAndView upload(
			@RequestParam("attached") CommonsMultipartFile[] files,
			String type, String newName) throws Exception {
		String userName = this.getUserDetails().getUsername();
		List<String> lt = Arrays.<String> asList(fileType_.split(","));
		for (int i = 0; i < files.length; i++) {
			CommonsMultipartFile file = files[i];
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				String fileExt = fileName
						.substring(fileName.lastIndexOf(".") + 1);
				if (!lt.contains(fileExt.toLowerCase())) {
					String message = "不允许上传<font color=red><b>" + fileExt
							+ "</b></font>类型的文件";
					FAILURE.addObject("mess", message);
					return FAILURE;
				}
				String uuid = UUID.randomUUID().toString();
				String uuidFileName = uuid + "." + fileExt;
				if (StringUtils.isNotBlank(newName)) {
					newName += "." + fileExt;
				}
				// 写入服务器磁盘并保存附件信息如库
				fileBiz.saveFileUpload(file, fileExt, type, savePath, newName,
						uuidFileName, userName, null);
			}
		}
		return SUCCESS;
	}

	/******************************** FineUpload Jcrop *****************************************/

	/**
	 * 上传图片（前台Fineupload）
	 * 
	 * @param file
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/clipUpload", method = RequestMethod.POST)
	public void clipUpload3(@RequestParam("attached") MultipartFile file,
			HttpServletResponse response) throws Exception {
		String tempName = fileBiz.saveClipImg(file, savePath, 400, 400, this
				.getUserDetails().getUsername());
		String s = "{\"success\":true,\"message\":\"sys/file/img/temp/"
				+ tempName + "\"}";
		response.getOutputStream().print(s);
	}

	/**
	 * 剪切图片（前台Jcrop）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/crop_form")
	public ModelAndView crop_form(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取剪切时坐标
		int x = (int)Math.rint(Double.parseDouble(request.getParameter("x")));
		int y = (int)Math.rint(Double.parseDouble(request.getParameter("y")));
		int w = (int)Math.rint(Double.parseDouble(request.getParameter("w")));
		int h = (int)Math.rint(Double.parseDouble(request.getParameter("h")));
		String filePath = request.getParameter("imgsrc");
		filePath = StringUtils.substringBetween(filePath, "/temp", "?id");
		// 上传文件类型
		String type = request.getParameter("type");

		String newName = fileBiz.saveCropImg(x, y, w, h, filePath, savePath,
				type, this.getUserDetails().getUsername());
		if (StringUtils.isNotBlank(newName)) {
			return SUCCESS.addObject("IsSuccess", true)
					.addObject("Message", "图片上传成功！")
					.addObject("FaceSrc", "sys/file/img/" + newName)
					.addObject("AvatarSrc", "sys/file/img/" + newName);
		} else {
			return FAILURE;
		}
	}

	/********************************* End ***********************************/

	/**
	 * 根据文件所在磁盘路径下载
	 * 
	 * 请求路径中url为文件在服务器端现在名
	 * 
	 * {name}.{fuffix}对应文件名格式例如： 原始名.png
	 * 
	 * @param url
	 * @param name
	 * @param fuffix
	 *            文件后缀
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "download/{url}/{name}.{fuffix}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable("url") String url,
			@PathVariable("name") String name,
			@PathVariable("fuffix") String fuffix) throws IOException {
		return fileBiz.initDownload(savePath + url, name + "." + fuffix);
	}

	/**
	 * 根据文件在库表中ID查找下载
	 * 
	 * @param id
	 * @param name
	 * @param url
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadById(@RequestParam("id") String id,
			String name, String url) throws IOException {
		if (StringUtils.isNotBlank(id)) {
			SYSAttached t = biz.findById(id);
			if (null != t) {
				url = t.getUrl();
				name = t.getName();
			}
		}
		return fileBiz.initDownload(savePath + url, name);
	}

	/**
	 * 支持批量删除文件或文件夾ByURL
	 * 
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "delete/{url}", method = RequestMethod.POST)
	public ModelAndView deleteByUrl(@PathVariable("url") String url) {
		String m = fileBiz.deleteFolderByUrl(url, savePath);
		String message = "";
		if (m.equals("1")) {
			message = "表中不存在!";
		} else if (m.equals("2")) {
			message = "被删除对象不存在!";
		} else if (m.equals("3")) {
			message = "删除文件时出错!";
		}
		if (!m.equals("0")) {
			return FAILURE.addObject("mess", message);
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 支持批量删除文件或文件夾ByID
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ModelAndView deleteById(@RequestParam("ids") String[] ids) {
		String m = fileBiz.deleteFolderById(ids, savePath);
		String message = "";
		if (m.equals("1")) {
			message = "表中不存在!";
		} else if (m.equals("2")) {
			message = "被删除对象不存在!";
		} else if (m.equals("3")) {
			message = "删除文件时出错!";
		}
		if (!m.equals("0")) {
			return FAILURE.addObject("mess", message);
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 读取savePaht下图片
	 * 
	 * @param response
	 * @param ext
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping("/img/{id}.{ext}")
	public void getImg(HttpServletResponse response,
			@PathVariable("ext") String ext, @PathVariable("id") String id)
			throws IOException {
		String iconPath = savePath + id + "." + ext;
		readImg(response, iconPath);
	}

	/**
	 * 读取savePaht+"/Temp"下临时图片
	 * 
	 * @param response
	 * @param ext
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping("/img/temp/{id}.{ext}")
	public void getTempImg(HttpServletResponse response,
			@PathVariable("ext") String ext, @PathVariable("id") String id)
			throws IOException {
		String iconPath = savePath + "Temp/" + id + "." + ext;
		readImg(response, iconPath);
	}

	private void readImg(HttpServletResponse response, String iconPath)
			throws FileNotFoundException, IOException {
		File file = new File(iconPath);
		log.info(iconPath);
		if (!file.exists()) {
			// 返回一个默认的图片流
			iconPath = savePath + File.separatorChar + "deleted.png";
		}
		FileInputStream fis = new FileInputStream(iconPath);
		int size = fis.available(); // 得到文件大小
		byte data[] = new byte[size];
		fis.read(data); // 读数据
		fis.close();
		String ex = iconPath.substring(iconPath.lastIndexOf(".") + 1,
				iconPath.length());
		response.setContentType("image/" + ex); // 设置返回的文件类型
		OutputStream os = response.getOutputStream();
		os.write(data);
		os.flush();
		os.close();
	}

	/**
	 * 上传文件请求异常，文件Size超过限制大小
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/error_MaxUploadSize")
	public ModelAndView forward_error_fileupload(ModelAndView mv) {
		String message = "上传<font color=red><b>失败</b></font>，文件大小已超过限定值<font color=blue><b>"
				+ maxUploadSize_M + "MB</b></font>！";
		FAILURE.addObject("mess", message);
		return FAILURE;
	}

	// ============= SpringMVC 与 IO（字节流）上传附件效率对比测试 ======
	// ============= 实验结果显示 IO 真的太慢了！！！ =========
	// =============Spring中 file.transferTo()与
	// FileCopyUtils.copy()前者比后者又快了很多啊！！！ =======

	/**
	 * 使用字节流上传
	 * 
	 * @param files
	 * @param fileType
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/upload_test_io")
	public void upload_IO(@RequestParam("attached") CommonsMultipartFile[] files)
			throws IllegalStateException, IOException {
		for (int i = 0; i < files.length; i++) {
			CommonsMultipartFile file = files[i];
			String myFileName = file.getOriginalFilename();
			log.info("-----Name::" + myFileName);
			if (!file.isEmpty()) {
				int startTime = (int) System.currentTimeMillis();
				String path = savePath + myFileName;
				// 构造输出流对象文件（创建一个将被写入内容的文件）
				FileOutputStream os = new FileOutputStream(path);
				// 构造上传文件的输入流（获取将要写入的内容）
				FileInputStream in = (FileInputStream) file.getInputStream();
				// 以写字节的方式写文件
				int b = 0;
				while ((b = in.read()) != -1) {
					os.write(b);
				}
				// 清空并关闭输入输出流
				os.flush();
				os.close();
				in.close();
				int finalTime = (int) System.currentTimeMillis();
				System.out.println("--->>>::" + (finalTime - startTime));
			}
		}
	}

	/**
	 * springMVC包装好地解析器上传
	 * 
	 * 此方法后台可以忽略前段附件字典name值
	 * 
	 * @param request
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/upload_test_springmvc")
	public void upload_SpringMVC(HttpServletRequest request)
			throws IllegalStateException, IOException {
		log.info("------");
		// 创建通用多部分解析器
		ServletContext sc = request.getSession().getServletContext();
		CommonsMultipartResolver mr = new CommonsMultipartResolver(sc);
		// 判断request是否有文件，即多部分请求
		if (mr.isMultipart(request)) {
			// 转换为多部分request
			MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
			// 取request中所有文件名
			Iterator<String> iter = mhsr.getFileNames();
			while (iter.hasNext()) {
				// 记录上传起始时间
				int startTime = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = mhsr.getFile(iter.next());
				if (null != file) {
					// 取当前文件名
					String myFileName = file.getOriginalFilename();
					if (StringUtils.isNotBlank(myFileName)) {
						log.info("-------Name:::" + myFileName);
						File localFile = new File(savePath + myFileName);
						file.transferTo(localFile);
						// FileOutputStream outputStream = new
						// FileOutputStream(localFile);
						// FileCopyUtils.copy(file.getInputStream(),
						// outputStream);
					}
				}
				// 记录上传该文件后时间
				int finalTime = (int) System.currentTimeMillis();
				System.out.println("--->>>::" + (finalTime - startTime));
			}
		}
	}

}
