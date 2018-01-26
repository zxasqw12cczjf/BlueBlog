package com.mrlonely.web.system.dao;

import com.mrlonely.common.base.BaseDao;
import com.mrlonely.web.system.entity.SysUser;

public interface SysUserDao extends BaseDao<SysUser, String> {

	public SysUser findByUsername(String username);
}
