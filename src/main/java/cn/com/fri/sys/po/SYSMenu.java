package cn.com.fri.sys.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author WYQ
 * 
 *         系统菜单Entity
 * 
 */
@Entity
@Table(name = "SYS_MENU")
@JsonIgnoreProperties(value = { "roles" })
public class SYSMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2247668389707372281L;

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;

	private String text;

	private String pid;

	private String icon;

	private String value;

	private String orderValue;

	private String remark;

	private String viewType;

	private boolean leaf;

	private boolean expanded;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_ROLE_MENU", joinColumns = { @JoinColumn(name = "menuid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) })
	private Set<SYSRole> roles = new HashSet<SYSRole>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// @Column(name = "text", length = 50)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	// @Column(name = "pid", length = 50)
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	// @Column(name = "icon", length = 300)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	// @Column(name = "value", length = 100)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// @Column(name = "ordervalue", length = 50)
	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	// @Column(name = "remark", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	// @Column(name = "leaf")
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	// @Column(name = "expanded")
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	// @Column(name = "viewtype", length = 50)
	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public Set<SYSRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SYSRole> roles) {
		this.roles = roles;
	}

}
