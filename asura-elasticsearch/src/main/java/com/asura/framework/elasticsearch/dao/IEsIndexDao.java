package com.asura.framework.elasticsearch.dao;

import com.asura.framework.elasticsearch.util.AnalyzeConstant;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;

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
public interface IEsIndexDao {
    /**
     * 创建索引
     * @param index
     * @return
     */
     boolean createIndex(String index);
    /**
     * 创建并设置索引
     * @param index
     * @return
     */
     boolean createIndexOnSettings(String index,Map<String, Object> settingsMap);
    /**
     * 删除索引
     * @param index
     * @return
     */
     boolean deleteIndex(String index);

    /**
     * 创建  索引、type mapping
     * @param indexName
     * @param indexType
     * @param mappingString
     * @return
     */
     boolean createType(String indexName, String indexType, String mappingString);

    /**
     * 创建索引及type mapping
     *
     * @param indexName
     *         索引名称
     * @param indexType
     *         索引类型
     * @param mappingMap
     *         索引mapping（map结构 key：字段名称 value：字段对应的mapping） 例如：
     *         Map<String, String> mappingMap = new HashMap<>();
     *         mappingMap.put("id", "type:integer,store:true");
     *         mappingMap.put("name", "type:string,store:true");
     *
     * @return
     */
    boolean createTypeOnMapping(String indexName, String indexType, Map<String, String> mappingMap);

    /**
     * 获取mapping
     * @param indexName
     * @param indexType
     * @return
     */
     ActionResponse getMapping(String indexName, String indexType);
    /**
     * 删除type
     * @param indexName
     * @param indexType
     * @return
     */
     boolean deleteType(String indexName, String indexType);
    /**
     * 检查索引是否存在
     * @param indexs
     * @return
     */
     boolean checkIndex(String ...indexs);
    /**
     * 检查type是否存在
     * @param indexName
     * @param typeNames
     * @return
     */
     boolean checkIndexTypes(String indexName,String ...typeNames);

    /**
     * 分词
     * @param indexName
     * @param keyWords
     * @param iKAnalyze
     * @return
     */
    AnalyzeResponse analyze(String indexName,String keyWords,AnalyzeConstant.IKAnalyze iKAnalyze);

}
