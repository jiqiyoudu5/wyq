package cn.com.fri.common.utils;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.persistence.Id;

import cn.com.fri.sys.dto.TreeDicDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 根据实体类映射相应文件内容
 * 
 * 1.ExtJs--->>model
 * 
 * @author WYQ
 * 
 */
public class ReflectObjectExtJS {

	public static void main(String[] args) {
		try {
			String model = ReflectObjectExtJS.createModel(TreeDicDto.class,
					"sys/attached", "sys.file");
			System.out.println(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param clazz
	 *            实体类名
	 * @param url
	 *            model中地址
	 * @param package_
	 *            model所在目录
	 * @return
	 * @throws IOException
	 */
	public static String createModel(Class<?> clazz, String url, String package_)
			throws IOException {
		String[] filters = null;
		// 判断该类中是否包含懒加载对象，如果包含懒加载对象，则排除在外。
		if (clazz.isAnnotationPresent(JsonIgnoreProperties.class)) {
			filters = clazz.getAnnotation(JsonIgnoreProperties.class).value();
		}
		Field[] fields = clazz.getDeclaredFields();

		String sb = "Ext.define('EJ.model." + package_ + "."
				+ clazz.getSimpleName() + "Model"
				+ "', {\r\n\textend: 'Ext.data.Model',\r\n\tfields:[";
		String idPropertyName = "";
		fieldFor: for (Field field : fields) {
			if (field.getName().equals("serialVersionUID")) {
				continue;
			}
			if (field.isAnnotationPresent(Id.class)) {
				idPropertyName = field.getName();
			}
			if (filters != null) {
				for (String f : filters) {
					if (f.equals(field.getName())) {
						continue fieldFor;
					}
				}
			}
			sb += "'" + field.getName() + "',";
		}
		sb = sb.substring(0, sb.lastIndexOf(","));
		sb += "],\r\n\tidProperty : '"
				+ idPropertyName
				+ "',\r\n\tproxy : {type : 'rest',actionMethods : {create : 'POST',read : 'GET',update : 'PUT',destroy : 'DELETE'},api : {create : '"
				+ url + "',read : '" + url + "',update : '" + url
				+ "',destroy : '" + url + "'}}\r\n\t});";
		// 在这里可以输出为文件，也可以返回一个字符串.
		return sb.toString();
	}

}