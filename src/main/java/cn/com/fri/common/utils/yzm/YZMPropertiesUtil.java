package cn.com.fri.common.utils.yzm;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 读取.properties文件(验证码文字字体)
 * 
 * @author WYQ
 * 
 */
public final class YZMPropertiesUtil {

	private static final Locale locale = new Locale("zh", "CN");

	private static final ResourceBundle rb = ResourceBundle.getBundle(
			"yzmFont", locale);

	public static String getValue(String name) {
		return rb.getString(name);
	}
}
