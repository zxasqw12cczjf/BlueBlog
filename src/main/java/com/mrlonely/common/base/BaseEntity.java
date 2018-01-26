package com.mrlonely.common.base;

import java.io.Serializable;
import java.util.Date;

import com.mrlonely.common.constant.Globals;

/**
 * Entity基础类
 * 
 * @author King
 *
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键ID */
	private String id;
	/** 创建人 */
	private String createUser;
	/** 创建日期 */
	private Date createDate;
	/** 修改人 */
	private String updateUser;
	/** 修改日期 */
	private Date updateDate;
	/** 删除标识(0:正常；1：删除)默认正常 */
	private String delFlag = Globals.USER_TYPE_NORMAL;
	
	/** 创建人名称 */
	private String createUserName;
	/** 修改人名称 */
	private String updateUserName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	
}
