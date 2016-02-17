package cn.com.fri.common.utils.yzm;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 生成图片验证码
 * 
 * @author WYQ
 * 
 */
public class OutImgAndCode {

	private String code = null;

	/**
	 * 验证码图片流、code存入session 名为:localValidateCode
	 * 
	 * @param reqeust
	 * 
	 * @param response
	 * 
	 * @param width
	 *            图片宽
	 * @param height
	 *            图片高
	 * @param codeCount
	 *            字符个数
	 * @param lineCount
	 *            干扰线条数
	 * @throws IOException
	 * 
	 */
	public OutImgAndCode(HttpServletRequest reqeust,
			HttpServletResponse response, int width, int height, int codeCount,
			int lineCount) throws IOException {
		this.intGet(reqeust, response, width, height, codeCount, lineCount);
	}

	/**
	 * 验证码图片流、code存入session 名为:localValidateCode
	 * 
	 * @param reqeust
	 * @param response
	 * @return 图片
	 * @throws IOException
	 */
	public void intGet(HttpServletRequest reqeust,
			HttpServletResponse response, int width, int height, int codeCount,
			int lineCount) throws IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = reqeust.getSession(true);
		ValidateCode vCode = new ValidateCode(width, height, codeCount,
				lineCount);
		session.setAttribute("localValidateCode", vCode.getCode());
		vCode.write(response.getOutputStream());
		code = vCode.getCode();
	}

	/**
	 * 图片上的字符串
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}
}
