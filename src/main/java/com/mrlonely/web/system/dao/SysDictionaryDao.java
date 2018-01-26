package com.mrlonely.web.system.dao;
import java.util.List;

import com.mrlonely.common.base.BaseDao;
import com.mrlonely.web.system.entity.SysDictionary;

/**
 * 
 * 数据字典dao接口.
 * 
 * <pre>
 * 	历史记录：
 * 	2017-01-07 23:45 King
 * 	新建文件
 * </pre>
 * 
 * @author 
 * <pre>
 * SD
 * 	King
 * PG
 *	King
 * UT
 *
 * MA
 * </pre>
 * @version $Rev$
 *
 * <p/> $Id$
 *
 */
public interface SysDictionaryDao extends BaseDao<SysDictionary, String> {

	/**
	 * 根据分组id获取信息
	 * 
	 * @param groupId
	 * @return
	 */
	public List<SysDictionary> getByGroupId(String groupId);
	
	/**
	 * 根据分组id删除信息
	 * 
	 * @param groupId
	 */
	public void deleteByGroupId(String groupId);
	
	/**
	 * 根据分组code获取信息
	 * 
	 * @param code
	 * @return
	 */
	public SysDictionary getByGroupCode(String code);
}