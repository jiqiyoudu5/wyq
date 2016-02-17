package cn.com.fri.sys.po;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "SYS_RESOURCE")
@JsonIgnoreProperties(value = { "roles" })
public class SYSResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1004028237978098522L;

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;

	private String url;

	private String name;

	private String endRegUser;

	private Date endUpdateTime;

	private String remark;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_ROLE_RESOURCE", joinColumns = { @JoinColumn(name = "resourceid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) })
	private Set<SYSRole> roles = new HashSet<SYSRole>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEndRegUser() {
		return endRegUser;
	}

	public void setEndRegUser(String endRegUser) {
		this.endRegUser = endRegUser;
	}

	public Date getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(Date endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<SYSRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SYSRole> roles) {
		this.roles = roles;
	}

}
