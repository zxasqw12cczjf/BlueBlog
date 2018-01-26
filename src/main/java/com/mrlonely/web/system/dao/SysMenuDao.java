package com.mrlonely.web.system.dao;

import java.util.List;

import com.mrlonely.common.base.BaseDao;
import com.mrlonely.web.system.entity.SysMenu;

public interface SysMenuDao extends BaseDao<SysMenu, String> {
	
	public List<SysMenu> getByParentId(String parentId);
	
	public void deleteByParentId(String parentId);
	
	public List<SysMenu> findForTreeTable(String parentId);
	
}
