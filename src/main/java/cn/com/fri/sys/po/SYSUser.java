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

/**
 * 用户信息实体类
 * 
 * @author WYQ
 * 
 */
@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties(value = { "roles" })
public class SYSUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4512750989145979302L;

	@Id
	@GenericGenerator(name = "generator", strategy = "assigned")
	@GeneratedValue(generator = "generator")
	@Column(name = "USERNAME", unique = true, nullable = false, length = 32)
	private String username;

	private String password;

	private Boolean enabled;

	private Boolean locked;

	private String businessName;

	private String phone;

	private String tel;

	private String email;

	private String address;

	private String district;

	private Date regTime;

	private Boolean vip;

	private Date vipendtime;

	private String remark;

	private String updateUser;

	private Date updateTime;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_USER_ROLE", joinColumns = { @JoinColumn(name = "username", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) })
	private Set<SYSRole> roles = new HashSet<SYSRole>(0);

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public Date getVipendtime() {
		return vipendtime;
	}

	public void setVipendtime(Date vipendtime) {
		this.vipendtime = vipendtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set<SYSRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SYSRole> roles) {
		this.roles = roles;
	}

}
