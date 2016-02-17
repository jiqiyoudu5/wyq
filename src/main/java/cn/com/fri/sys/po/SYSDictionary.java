package cn.com.fri.sys.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.com.fri.sys.utils.annotations.TreeID;
import cn.com.fri.sys.utils.annotations.TreeParentcode;
import cn.com.fri.sys.utils.annotations.TreeText;
import cn.com.fri.sys.utils.annotations.TreeType;
import cn.com.fri.sys.utils.annotations.TreeValue;

/**
 * 系统字典表
 * 
 * @author WYQ
 * 
 */
@Entity
@Table(name = "sys_dictionary")
public class SYSDictionary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3470315674051927232L;

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 100)
	@TreeID
	private String id;

	@TreeType
	private String type;

	@TreeParentcode
	private String parent;

	@TreeText
	private String text;

	@TreeValue
	private String remark;

	private String orderValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
