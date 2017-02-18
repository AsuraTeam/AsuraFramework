/**
 * @FileName: BaseIbatisDAO.java
 * @Package com.asura.framework.dao.old
 * 
 * @author zhangshaobin
 * @created 2014年10月21日 上午10:31:24
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dao.old;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchCondition;
import com.asura.framework.base.paging.SearchModel;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
	
/**
 * <p>
 * TODO
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class BaseIbatisDAO {
	
	/**
	 * Spring封装的iBatis模板
	 */
	private SqlMapClientTemplate template;
	
	/**
	 * 
	 * 设置当前iBatis映射信息
	 *
	 * @author zhangshaobin
	 * @created 2012-12-13 下午12:31:20
	 *
	 * @param sqlMapClient	映射信息
	 */
	public void setCurrentSqlMapClient(SqlMapClient sqlMapClient) {
		SqlMapClientDaoSupport ibatisOperator = new SqlMapClientDaoSupport() {};
		ibatisOperator.setSqlMapClient(sqlMapClient);
		template = ibatisOperator.getSqlMapClientTemplate();
	}
	
	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:32:51
	 *
	 * @param sqlId	SQL语句ID
	 * @return	查询到的实体集合
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> findAll(String sqlId) {
		return (List<T>) template.queryForList(sqlId);
	}

	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:34:26
	 *
	 * @param sqlId	SQL语句ID
	 * @param SearchModel	查询模型
	 * @return	查询到的实体集合
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> findAll(String sqlId, SearchModel searchModel) {
		return (List<T>) template.queryForList(sqlId, getConditionMap(searchModel));
	}
	
	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午5:37:47
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	条件参数
	 * @return	查询到的结果集合
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> findAll(String sqlId, Map<String, Object> params) {
		return (List<T>) template.queryForList(sqlId, params);
	}
	
	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2012-12-19 下午5:51:23
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param params	条件参数
	 * @return	查询到的结果集合
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(String sqlId, Class<T> clazz, Map<String, Object> params) {
		return (List<T>)template.queryForList(sqlId, params);
	}
	
	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2012-12-24 下午5:11:46
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param param	条件参数
	 * @return	查询到的结果集合
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(String sqlId, Class<T> clazz, int param) {
		return (List<T>)template.queryForList(sqlId, param);
	}
	
	/**
	 * 
	 * 分页查询指定SQL语句的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:39:36
	 *
	 * @param countSqlId	总数查询SQL语句ID
	 * @param sqlId	SQL语句ID
	 * @param searchModel	查询模型
	 * @param clazz	查询结果类型
	 * @return	分页查询结果PagingResult
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> PagingResult<T> findForPage(String countSqlId, String sqlId, SearchModel searchModel, Class<T> clazz) {
		List<T> list = template.queryForList(sqlId, getConditionMap(searchModel), 
				searchModel.getFirstRowNum(), searchModel.getPageSize());
		return new PagingResult<T>(count(countSqlId, searchModel), list);
	}
	
	/**
	 * 
	 * 分页查询指定SQL语句的数据（ID）
	 *
	 * @author zhangshaobin
	 * @created 2013-1-25 下午4:10:53
	 *
	 * @param countSqlId	总数查询SQL语句ID
	 * @param sqlId	SQL语句ID
	 * @param searchModel	查询模型
	 * @return	分页查询结果List<Integer>
	 */
	@SuppressWarnings("unchecked")
	public PagingResult<Integer> findForPage(String countSqlId, String sqlId, SearchModel searchModel) {
		List<Integer> list = template.queryForList(sqlId, getConditionMap(searchModel), 
				searchModel.getFirstRowNum(), searchModel.getPageSize());
		return new PagingResult<Integer>(count(countSqlId, searchModel), list);
	}
	
	/**
	 * 
	 * 查询指定SQL语句的一条记录
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:45:55
	 *
	 * @param sqlId	SQL语句ID
	 * @return	查询到的实体
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T findOne(String sqlId) {
		return (T)template.queryForObject(sqlId);
	}
	
	/**
	 * 
	 * 根据条件查询指定SQL语句的一条记录
	 *
	 * @author zhangshaobin
	 * @created 2012-12-14 下午6:29:09
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param params	条件参数
	 * @return	查询到的结果
	 */
	@SuppressWarnings("unchecked")
	public <T> T findOne(String sqlId, Class<T> clazz, Map<String, Object> params) {
		return (T)template.queryForObject(sqlId, params);
	}
	
	/**
	 * 
	 * 根据条件查询指定SQL语句的一条记录，主要用于关联查询的情况
	 *
	 * @author zhangshaobin
	 * @created 2012-12-24 下午5:02:04
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param param	条件参数
	 * @return	查询到的结果
	 */
	@SuppressWarnings("unchecked")
	public <T> T findOne(String sqlId, Class<T> clazz, int param) {
		return (T)template.queryForObject(sqlId, param);
	}

	/**
	 * 
	 * 查询指定SQL语句的一条记录
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:48:02
	 *
	 * @param sqlId	SQL语句ID
	 * @param param	SQL语句中占位符对应的值
	 * @return	查询到的实体
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T findOne(String sqlId, SearchModel searchModel) {
		return (T)template.queryForObject(sqlId, getConditionMap(searchModel));
	}
	
	/**
	 * 
	 * 查询指定SQL语句所包含的记录数
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:52:26
	 *
	 * @param sqlId	SQL语句ID
	 * @return	记录数
	 */
	public long count(String sqlId) {
		return (Long) template.queryForObject(sqlId);
	}
	
	/**
	 * 
	 * 查询指定SQL语句所包含的记录数
	 *
	 * @author zhangshaobin
	 * @created 2013-6-7 上午10:16:05
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	参数
	 * @return	符合条件的记录数
	 */
	public long count(String sqlId, Map<String, Object> params) {
		return (Long) template.queryForObject(sqlId, params);
	}

	/**
	 * 
	 * 查询指定SQL语句所包含的记录数
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:55:36
	 *
	 * @param sqlId	SQL语句ID
	 * @param searchModel	查询模型
	 * @return	符合条件的记录数
	 */
	public long count(String sqlId, SearchModel searchModel) {
		return (Long) template.queryForObject(sqlId, getConditionMap(searchModel));
	}

	/**
	 * 
	 * 插入指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:59:43
	 *
	 * @param sqlId	SQL语句ID
	 * @return	插入对象的主键
	 */
	public Object save(String sqlId) {
		return template.insert(sqlId);
	}

	/**
	 * 
	 * 插入指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:15:07
	 *
	 * @param sqlId	SQL语句ID
	 * @param entity	要插入的实体
	 * @return	插入对象的主键
	 */
	public Object save(String sqlId, BaseEntity entity) {
		return template.insert(sqlId, entity);
	}

	/**
	 * 
	 * 更新指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:18:34
	 *
	 * @param sqlId	SQL语句ID
	 * @return	成功更新的记录数
	 */
	public int update(String sqlId) {
		return template.update(sqlId);
	}

	/**
	 * 
	 * 更新指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:25:49
	 *
	 * @param sqlId	SQL语句ID
	 * @param entity	要更新的对象
	 * @return	成功更新的记录数
	 */
	public int update(String sqlId, BaseEntity entity) {
		return template.update(sqlId, entity);
	}
	
	/**
	 * 
	 * 更新指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-12-17 下午6:38:55
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	参数
	 * @return	成功更新的记录数
	 */
	public int update(String sqlId, Map<String, Object> params) {
		return template.update(sqlId, params);
	}

	/**
	 * 
	 * 删除指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:30:00
	 *
	 * @param sqlId	SQL语句ID
	 * @return	成功删除的记录数
	 */
	public int delete(String sqlId) {
		return template.delete(sqlId);
	}

	/**
	 * 
	 * 删除指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:33:17
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	查询参数
	 * @return	成功删除记录数
	 */
	public int delete(String sqlId, Map<String, Object> params) {
		return template.delete(sqlId, params);
	}
	
	/**
	 * 
	 * 批量删除指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-12-3 下午2:19:55
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	SQL语句中占位符对应的值
	 * @return	成功更新的记录数
	 */
	public int[] batchDelete(final String sqlId, final List<BaseEntity> params) {
		// 执行回调
		SqlMapClientCallback<Object> callback = new SqlMapClientCallback<Object>() {
			// 实现回调接口
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				// 开始批处理
				executor.startBatch();
				int[] rtnDel = new int[params.size()];
				for (int i = 0; i < params.size(); i++) {
					rtnDel[i] = executor.delete(sqlId, params.get(i));
				}
				// 执行批处理
				executor.executeBatch();
				return rtnDel;
			}
		};

		return (int[]) template.execute(callback);
	}

	/**
	 * 
	 * 批量更新指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:36:32
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	SQL语句中占位符对应的值
	 * @return	成功更新的记录数
	 */
	public int[] batchUpdate(final String sqlId, final List<? extends BaseEntity> params) {
		// 执行回调
		SqlMapClientCallback<Object> callback = new SqlMapClientCallback<Object>() {
			// 实现回调接口
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				// 开始批处理
				executor.startBatch();
				int[] rtnUpd = new int[params.size()];
				for (int i = 0; i < params.size(); i++) {
					rtnUpd[i] = executor.update(sqlId, params.get(i));
				}
				// 执行批处理
				executor.executeBatch();
				return rtnUpd;
			}
		};

		return (int[]) template.execute(callback);
	}
	
	/**
	 * 
	 * 以Map形式得到所有查询条件，key为参数名称对应SearchCondition的name，value为参数值对应SearchCondition的value
	 *
	 * @author zhangshaobin
	 * @created 2012-12-13 下午2:33:36
	 *
	 * @param searchModel	查询模型
	 * @return	Map形式的参数信息
	 */
	private Map<String, Object> getConditionMap(SearchModel searchModel) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		if (searchModel.getSearchConditionList() != null) {
			for (SearchCondition condition : searchModel.getSearchConditionList()) {
				conditionMap.put(condition.getName(), condition.getValue());
			}
		}
		return conditionMap;
	}
	
}
