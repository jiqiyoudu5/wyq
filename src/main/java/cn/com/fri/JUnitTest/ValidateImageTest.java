package cn.com.fri.JUnitTest;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import cn.com.fri.JUnitTest.base.JUnitBaseBiz;
import cn.com.fri.common.utils.yzm.ValidateCode;

public class ValidateImageTest extends JUnitBaseBiz {

	/**
	 * 生成验证码图片
	 */
	@Test
	public void outimage() {
		ValidateCode vCode = new ValidateCode(120, 40, 5, 50);
		try {
			String path = "D:/" + new Date().getTime() + ".png";
			System.out.println(vCode.getCode() + " >" + path);
			vCode.write(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
