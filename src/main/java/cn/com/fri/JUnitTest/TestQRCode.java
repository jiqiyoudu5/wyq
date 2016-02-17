package cn.com.fri.JUnitTest;

import org.junit.Test;

import cn.com.fri.JUnitTest.base.JUnitBaseBiz;
import cn.com.fri.common.utils.QRCode.QRCodeUtil;

public class TestQRCode extends JUnitBaseBiz {

	@Test
	public void test1() throws Exception {

		// 生成二维码
		QRCodeUtil.encode("海贼王", "d:/user3.png", "d:/a/", true);

		// 读二维码内容
		// log.info(QRCodeUtil.decode("d:/a/10733774.jpg"));

	}

}
