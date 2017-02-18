package com.asura.framework.elasticsearch.dao;

import com.asura.framework.elasticsearch.EsResponse;
import com.asura.framework.elasticsearch.exception.EsException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jiangn18
 * @version 1.0
 * @date 2016/8/30 10:59
 * @since 1.0
 */
public interface IEsQueryDao {

    /**
     * 查询
     * @return
     * @throws Exception
     */
    EsResponse queryByEsQueryDo(EsQueryDo esQueryObj) throws EsException;

    /**
     *
     * @param searchSourceBuilder
     * @param indexName
     * @param typeNames
     * @return
     * @throws EsException
     */
    SearchResponse query(SearchSourceBuilder searchSourceBuilder, String indexName, String... typeNames) throws EsException;




}
