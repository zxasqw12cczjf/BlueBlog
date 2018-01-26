package com.mrlonely.web.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mrlonely.common.persistence.Page;
import com.mrlonely.common.util.ResourceUtils;
import com.mrlonely.web.system.dao.SysRoleMenuDao;
import com.mrlonely.web.system.dao.SysUserDao;
import com.mrlonely.web.system.dao.SysUserRoleDao;
import com.mrlonely.web.system.entity.SysUser;
import com.mrlonely.web.system.entity.SysUserRole;
import com.mrlonely.web.system.service.SysUserService;

@Service("SysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysUserRoleDao sysUserRoleDao;
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Override
	public SysUser findByUsername(SysUser user) {
		
		return sysUserDao.findByUsername(user.getUsername());
	}

	@Override
	public Page<SysUser> findByPage(SysUser user, Page<SysUser> page) {
		
		page.setResult(sysUserDao.find(user, page));
		
		return page;
	}

	@Override
	public SysUser getById(String id) {
		
		return sysUserDao.getById(id);
	}

	@Override
	public void add(SysUser user) {
		
		user.setId(ResourceUtils.getUUID());
		sysUserDao.add(user);
		
		if(StringUtils.isNotEmpty(user.getRoleIds())){
			List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
			for(String roleId : user.getRoleIds().split(",")){
				if(StringUtils.isNotEmpty(roleId)){
					SysUserRole userRole = new SysUserRole();
					userRole.setId(ResourceUtils.getUUID());
					userRole.setRoleId(roleId);
					userRole.setUserId(user.getId());
					userRoleList.add(userRole);
				}
			}
			
			if(userRoleList.size() > 0){
				sysUserRoleDao.batchAdd(userRoleList);
			}
		}
	}

	@Override
	public void update(SysUser user) {
		
		sysUserDao.update(user);
		
		// 删除旧数据
		sysUserRoleDao.deleteByUserId(user.getId());
		
		if(StringUtils.isNotEmpty(user.getRoleIds())){
			List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
			for(String roleId : user.getRoleIds().split(",")){
				if(StringUtils.isNotEmpty(roleId)){
					SysUserRole userRole = new SysUserRole();
					userRole.setId(ResourceUtils.getUUID());
					userRole.setRoleId(roleId);
					userRole.setUserId(user.getId());
					userRoleList.add(userRole);
				}
			}
			
			if(userRoleList.size() > 0){
				sysUserRoleDao.batchAdd(userRoleList);
			}
		}
	}

	@Override
	public void delete(String id) {
		
		sysUserDao.delete(id);
		// 删除旧数据
		sysUserRoleDao.deleteByUserId(id);
	}

	@Override
	public List<String> findByUserId(String userId) {
		
		return sysUserRoleDao.findByUserId(userId);
	}

	@Override
	public List<String> findMenuIdByUserId(String userId) {
		
		return sysRoleMenuDao.findMenuIdByUserId(userId);
	}

	
}
