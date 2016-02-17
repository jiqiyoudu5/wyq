package cn.com.fri.sys.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 读取.properties文件(系统常量)
 * 
 * @author WYQ
 * 
 */
public final class SYSPropertiesUtil {

	private static final Locale locale = new Locale("zh", "CN");

	private static final ResourceBundle rb = ResourceBundle.getBundle(
			"config/conf", locale);

	public static String getValue(String name) {
		return rb.getString(name);
	}
}
