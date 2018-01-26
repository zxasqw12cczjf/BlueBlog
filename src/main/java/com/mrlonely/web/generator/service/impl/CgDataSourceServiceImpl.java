package com.mrlonely.web.generator.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mrlonely.common.persistence.Page;
import com.mrlonely.common.util.ResourceUtils;
import com.mrlonely.web.generator.dao.CgDataSourceDao;
import com.mrlonely.web.generator.entity.CgDataSource;
import com.mrlonely.web.generator.service.CgDataSourceService;

@Service("CgDataSourceService")
public class CgDataSourceServiceImpl implements CgDataSourceService {

	@Resource
	private CgDataSourceDao cgDataSourceDao;
	
	@Override
	public Page<CgDataSource> findByPage(CgDataSource dataSource, Page<CgDataSource> page) {
		
		page.setResult(cgDataSourceDao.find(dataSource,page));
		
		return page;
	}
	
	@Override
	public List<CgDataSource> findBySearch(CgDataSource dataSource){
		
		return cgDataSourceDao.find(dataSource);
	}

	@Override
	public CgDataSource getById(String id) {
		
		return cgDataSourceDao.getById(id);
	}

	@Override
	public void add(CgDataSource dataSource) throws Exception {
		
		dataSource.setId(ResourceUtils.getUUID());
		cgDataSourceDao.add(dataSource);
	}

	@Override
	public void update(CgDataSource dataSource) throws Exception {
		
		cgDataSourceDao.update(dataSource);
	}

	@Override
	public void delete(String id) throws Exception {
		
		cgDataSourceDao.delete(id);
	}

}
