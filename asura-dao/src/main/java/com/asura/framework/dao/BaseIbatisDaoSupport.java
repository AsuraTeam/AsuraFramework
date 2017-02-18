/**
 * @FileName: BaseIbatisDaoSupport.java
 * @Package com.sfbest.framework.dao
 * 
 * @author zhangshaobin
 * @created 2013-12-6 下午2:50:50
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.exception.DataAccessException;
import com.asura.framework.dao.rule.Rule;
import com.asura.framework.dao.rule.ShardTableRule;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 基类
 * @since 1.0
 * @version 1.0
 */
public class BaseIbatisDaoSupport implements InitializingBean {
	protected Logger logger = LoggerFactory.getLogger(getClass().getName());
	protected SqlMapClient readSqlMapClient;

	protected SqlMapClient writeSqlMapClient;
	protected Map<String, Rule> routerMap; //分表路由集合

	protected SqlMapClientTemplate writeTemplate; //写库模板
	protected SqlMapClientTemplate readTemplate; //读库模板

	protected ShardTableRule shardTableRule; //分表路由对象

	protected SqlMapClientTemplate getWriteSqlMapClientTemplate() {
		if (null == writeSqlMapClient)
			logger.error("Shit ,,check properties !!! writeSqlMapClient NULL!! ");
		if (null == writeTemplate)
			writeTemplate = new SqlMapClientTemplate(writeSqlMapClient);
		return writeTemplate;
	}

	protected SqlMapClientTemplate getReadSqlMapClientTemplate() {
		if (null == readSqlMapClient)
			logger.error("Shit ,,check properties !!! readSqlMapClient NULL !! ");
		if (null == readTemplate)
			readTemplate = new SqlMapClientTemplate(readSqlMapClient);
		return readTemplate;
	}

	/**
	 * @return the shardTableRule
	 */
	public ShardTableRule getShardTableRule() {
		return shardTableRule;
	}

	/**
	 * @param shardTableRule the shardTableRule to set
	 */
	public void setShardTableRule(final ShardTableRule shardTableRule) {
		this.shardTableRule = shardTableRule;
	}

	/**
	 * @return the readSqlMapClient
	 */
	public SqlMapClient getReadSqlMapClient() {
		return readSqlMapClient;
	}

	/**
	 * @param readSqlMapClient the readSqlMapClient to set
	 */
	public void setReadSqlMapClient(final SqlMapClient readSqlMapClient) {
		this.readSqlMapClient = readSqlMapClient;
	}

	/**
	 * @return the writeSqlMapClient
	 */
	public SqlMapClient getWriteSqlMapClient() {
		return writeSqlMapClient;
	}

	/**
	 * @param writeSqlMapClient the writeSqlMapClient to set
	 */
	public void setWriteSqlMapClient(final SqlMapClient writeSqlMapClient) {
		this.writeSqlMapClient = writeSqlMapClient;
	}

	protected void setSqlMapClient(final SqlMapClient sqlMapClient) {
		setWriteSqlMapClient(sqlMapClient);
		setReadSqlMapClient(sqlMapClient);
	}

	protected void setSqlMapClient(final SqlMapClient writesqlMapClient, final SqlMapClient readsqlMapClient) {
		setWriteSqlMapClient(writesqlMapClient);
		setReadSqlMapClient(readsqlMapClient);
	}

	/**
	 * 
	 * 对分表sqlid对应的参数进行封装转换；对未分表的sqlid不处理；
	 * @created 2013-12-27 下午4:47:29
	 *
	 * @param sqlId ： ibatis的目标语句
	 * @param paramsObj   BaseBean  Map int long 等类型
	 * @return sqlid语句对应的参数
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	protected Object executeRouter(final String sqlId, final Object paramsObj) throws DataAccessException {
		//不分表则返回参数
		if (!isGoRule(sqlId)) {
			logger.debug(sqlId + " not shardtable!");
			return paramsObj;
		}
		//分表流程
		if (paramsObj == null) {
			throw new DataAccessException(sqlId + " ,param is null ,error!!");
		}
		Map<String, Object> params = null;
		if (paramsObj instanceof Map) {
			logger.debug("shard param is map !!! ");
			params = (Map<String, Object>) paramsObj;
		} else if (paramsObj instanceof BaseEntity) {
			logger.debug("shard param is BaseEntity!!! ");
			params = ((BaseEntity) paramsObj).toMap();
		} else {
			//param is long int or string 
			logger.debug("shard param is neither map nor BaseEntity!!! ");
			params = new HashMap<String, Object>();
			params.put(routerMap.get(sqlId).getFieldParam(), paramsObj);

		}
		return executeRouter(sqlId, params);
	}

	private Map<String, Object> executeRouter(final String sqlId, final Map<String, Object> params)
			throws DataAccessException {

		//走分表,必须带分表字段参数
		final Rule router = routerMap.get(sqlId);
		if (params == null || !params.containsKey(router.getFieldParam())) {
			throw new DataAccessException(sqlId + " without routerID!!,error!!");
		}
		final Object routerKeyValue = params.get(router.getFieldParam());

		if (routerKeyValue == null) {
			throw new DataAccessException(sqlId + " routerID is null !! error!!");
		} else if (routerKeyValue instanceof Integer && (Integer) routerKeyValue == 0) {
			throw new DataAccessException(sqlId + " routerID is 0 !! error!!");
		} else if (routerKeyValue instanceof Long && (Long) routerKeyValue == 0L) {
			throw new DataAccessException(sqlId + " routerID is 0 !! error!!");
		} else if (routerKeyValue instanceof String && routerKeyValue.equals("")) {
			throw new DataAccessException(sqlId + " routerID is '' !! error!!");
		}
		final String tableName = router.getShardStrategy().getTargetTable(routerKeyValue);
		params.put(router.getXmlTableParam(), tableName);
		return params;
	}

	/**
	 * 
	 * 是否分表
	 * @created 2013-12-30 上午9:23:31
	 *
	 * @param sqlId
	 * @return
	 */
	private boolean isGoRule(final String sqlId) {
		return routerMap == null ? false : routerMap.containsKey(sqlId);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//loadRules() ; 
		routerMap = shardTableRule == null ? null : shardTableRule.getRouterMap();
	}

}
