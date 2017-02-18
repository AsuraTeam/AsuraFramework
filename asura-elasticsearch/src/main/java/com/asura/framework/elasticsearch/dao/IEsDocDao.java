package com.asura.framework.elasticsearch.dao;

import com.asura.framework.elasticsearch.exception.EsDocException;
import com.asura.framework.elasticsearch.po.DocPo;
import org.elasticsearch.action.bulk.BulkResponse;

import java.util.List;
import java.util.Map;

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
public interface IEsDocDao {
    /**
     * 修改文档
     * @param indexName
     * @param indexType
     * @param id
     * @param obj
     * @return
     */
     boolean  updateDocument(String indexName, String indexType,String id,Object obj);
    /**
     * 新增文档
     * @param indexName
     * @param indexType
     * @param obj
     * @return
     */
     boolean  createDocument(String indexName, String indexType,Object obj);

    /**
     * 更改局部字段
     *
     * @param indexName
     * @param indexType
     * @param id
     * @param fieldValues
     *
     * @return
     */
    boolean updateFileds(String indexName, String indexType, String id, Map<String, Object> fieldValues);
    /**
     * 保存文档
     * @param indexName
     * @param indexType
     * @param obj
     * @return
     */
     boolean  saveOrUpdateDocument(String indexName, String indexType,String id,Object obj);

    /**
     * 批量save or update
     * @param indexName
     * @param indexType
     * @param list
     * @return
     */
     List<BulkResponse> bulkSaveOrUpdates(String indexName, String indexType, List<DocPo> list,int len) throws EsDocException;
    /**
     * 批量save or update   默认处理1000个
     * @param indexName
     * @param indexType
     * @param list
     * @return
     */
      List<BulkResponse> bulkSaveOrUpdates(String indexName, String indexType, List<DocPo> list) throws   EsDocException;
    /**
     * 删除文档
     * @param indexName
     * @param indexType
     * @param id
     * @return
     */
    boolean deleteDocument(String indexName, String indexType, String id);
    /**
     * delete
     * @param indexName
     * @param indexType
     * @param list
     * @return
     */
      List<BulkResponse> bulkDeletes(String indexName, String indexType, List<String> list,int len) throws   EsDocException;
    /**
     * delete 默认1000个
     * @param indexName
     * @param indexType
     * @param list
     * @return
     */
     List<BulkResponse> bulkDeletes(String indexName, String indexType, List<String> list) throws   EsDocException;



    }
