package com.mrlonely.web.system.dao;

import java.util.List;

import com.mrlonely.common.base.BaseDao;
import com.mrlonely.web.system.entity.SysOrg;

public interface SysOrgDao extends BaseDao<SysOrg, String> {

	public List<SysOrg> findForTreeTable(String parentId);
	
	public void deleteByParentId(String parentId);
	
	public String getMaxCode(String parentId);
}
