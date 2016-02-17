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

@Entity
@Table(name = "sys_role")
@JsonIgnoreProperties(value = { "users", "resources", "menus" })
public class SYSRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7529810543443721108L;

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;

	private String code;

	private String name;

	private String remark;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_USER_ROLE", joinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "username", nullable = false, updatable = false) })
	private Set<SYSUser> users = new HashSet<SYSUser>(0);

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_ROLE_RESOURCE", joinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "resourceid", nullable = false, updatable = false) })
	private Set<SYSResource> resources = new HashSet<SYSResource>(0);

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_ROLE_MENU", joinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "menuid", nullable = false, updatable = false) })
	private Set<SYSMenu> menus = new HashSet<SYSMenu>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<SYSUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SYSUser> users) {
		this.users = users;
	}

	public Set<SYSResource> getResources() {
		return resources;
	}

	public void setResources(Set<SYSResource> resources) {
		this.resources = resources;
	}

	public Set<SYSMenu> getMenus() {
		return menus;
	}

	public void setMenus(Set<SYSMenu> menus) {
		this.menus = menus;
	}

}
