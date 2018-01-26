package com.mrlonely.web.generator.service;

import java.util.List;

import com.mrlonely.common.persistence.Page;
import com.mrlonely.web.generator.entity.CgDataSource;

public interface CgDataSourceService {

	public Page<CgDataSource> findByPage(CgDataSource dataSource,Page<CgDataSource> page);
	
	public List<CgDataSource> findBySearch(CgDataSource dataSource);
	
	public CgDataSource getById(String id);
	
	public void add(CgDataSource dataSource) throws Exception;
	
	public void update(CgDataSource dataSource) throws Exception;
	
	public void delete(String dataSourceId) throws Exception;
}
