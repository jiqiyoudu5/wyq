package cn.com.fri.sys.utils;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.fri.sys.dto.TreeDicDto;
import cn.com.fri.sys.utils.annotations.TreeID;
import cn.com.fri.sys.utils.annotations.TreeParentcode;
import cn.com.fri.sys.utils.annotations.TreeText;
import cn.com.fri.sys.utils.annotations.TreeType;
import cn.com.fri.sys.utils.annotations.TreeValue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class TreeUtil {

	private static final Logger log = LoggerFactory.getLogger(TreeUtil.class);

	/**
	 * 将对象转换成实体类
	 * 
	 * @param dto
	 * @param o
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static TreeDicDto convertToTree(TreeDicDto dto, Object o)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> c = o.getClass();

		String[] filters = null;
		// 判断该类中是否包含懒加载对象，如果包含懒加载对象，则排除在外。
		if (c.isAnnotationPresent(JsonIgnoreProperties.class)) {
			filters = c.getAnnotation(JsonIgnoreProperties.class).value();
		}
		Field[] fs = c.getDeclaredFields();

		Field.setAccessible(fs, true);

		fieldFor: for (Field field : fs) {
			if (filters != null) {
				for (String f : filters) {
					if (f.equals(field.getName())) {
						continue fieldFor;
					}
				}
			}
			if (field.get(o) == null) {
				// log.warn("在转换TreeDicDto的时候，发现" + field.getName() +
				// "的值是Null");
				continue;
			}

			String v = field.get(o).toString();

			// 把对象o属性id值set到dto中以下雷同（把o中需要的值set到dto中）
			if (field.isAnnotationPresent(TreeID.class)) {
				dto.setId(v);
			}

			if (field.isAnnotationPresent(TreeType.class)) {
				dto.setType(v);
			}

			if (field.isAnnotationPresent(TreeText.class)) {
				dto.setText(v);
			}

			if (field.isAnnotationPresent(TreeParentcode.class)) {
				dto.setParent(v);
			}

			if (field.isAnnotationPresent(TreeValue.class)) {
				dto.setValue(v);
			}

		}
		return dto;
	}

}
