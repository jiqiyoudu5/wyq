package cn.com.fri.spring.security.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.fri.basic.controller.BaseController;
import cn.com.fri.common.utils.yzm.OutImgAndCode;

@Controller
@RequestMapping("/validate/yzm")
public class ValidateCodeController extends BaseController {

	@RequestMapping(value = "/get", method = { RequestMethod.POST,
			RequestMethod.GET })
	public void getValidateImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 生成图片
		new OutImgAndCode(request, response, 200, 50, 4, 50);
		// 生成图片上的字符
		String str = (String) request.getSession().getAttribute(
				"localValidateCode");
		log.info("----系统生成的验证码---->>>>>:::" + str);
	}

}
