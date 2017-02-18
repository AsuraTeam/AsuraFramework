package com.asura.framework.elasticsearch.dao;

import com.asura.framework.elasticsearch.client.EshClientFactory;
import com.asura.framework.elasticsearch.util.AnalyzeConstant;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
 * @date 2016/8/29 21:44
 * @since 1.0
 */
public class EsIndex implements IEsIndexDao {
    private static Logger logger = LoggerFactory.getLogger(EsIndex.class);

    private EshClientFactory esClientFactory;//ES 客户端工厂类

    /**
     * 创建索引
     *
     * @param indexName
     *
     * @return
     */
    public boolean createIndex(String indexName) {
        TransportClient client = esClientFactory.getClient();
        CreateIndexResponse response = null;
        // 如果存在返回true
        if (client.admin().indices().prepareExists(esClientFactory.getIndexs(indexName)).get().isExists()) {
            return true;
        } else {
            response = client.admin().indices().prepareCreate(esClientFactory.getIndexs(indexName)).execute().actionGet();
        }
        return response.isAcknowledged();
    }

    /**
     * 创建并设置索引
     *
     * @param indexName
     *
     * @return
     */
    public boolean createIndexOnSettings(String indexName, Map<String, Object> settingsMap) {
        TransportClient client = esClientFactory.getClient();
        CreateIndexResponse response = null;
        if (client.admin().indices().prepareExists(esClientFactory.getIndexs(indexName)).get().isExists()) {
            return true;
        }else{
             response = client.admin().indices().prepareCreate(esClientFactory.getIndexs(indexName)).setSettings(settingsMap).get();
        }
        return response.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param indexName
     *
     * @return
     */
    public boolean deleteIndex(String indexName) {
        TransportClient client = esClientFactory.getClient();
        DeleteIndexResponse response = client.admin().indices().prepareDelete(esClientFactory.getIndexs(indexName)).execute().actionGet();
        return response.isAcknowledged();
    }

    /**
     * 创建  索引、type mapping
     *
     * @param indexName
     * @param indexType
     * @param mappingString
     *
     * @return
     */
    public boolean createType(String indexName, String indexType, String mappingString) {
        TransportClient client = esClientFactory.getClient();
        if (!client.admin().indices().prepareExists(indexName).get().isExists()) {
            CreateIndexResponse response = client.admin().indices().prepareCreate(esClientFactory.getIndexs(indexName)).addMapping(indexType, mappingString).get();
            return response.isAcknowledged();
        } else {
            PutMappingResponse response = client.admin().indices().preparePutMapping(esClientFactory.getIndexs(indexName)).setType(indexType).setSource(mappingString).get();
            return response.isAcknowledged();
        }
    }

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
    public boolean createTypeOnMapping(String indexName, String indexType, Map<String, String> mappingMap) {
        TransportClient client = esClientFactory.getClient();
        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject().startObject("properties");
            for (Map.Entry<String, String> fieldEntry : mappingMap.entrySet()) {
                contentBuilder.startObject(fieldEntry.getKey().trim());
                String[] fieldMappings = fieldEntry.getValue().split(",");
                for (String fieldMapping : fieldMappings) {
                    String[] mapping = fieldMapping.split(":");
                    if (mapping.length == 2) {
                        contentBuilder.field(mapping[0].trim(), mapping[1].trim());
                    }
                }
                contentBuilder.endObject();
            }
            contentBuilder.endObject().endObject();

            if (!client.admin().indices().prepareExists(esClientFactory.getIndexs(indexName)).get().isExists()) {
                CreateIndexResponse response = client.admin().indices().prepareCreate(esClientFactory.getIndexs(indexName)).addMapping(indexType, contentBuilder).get();
                return response.isAcknowledged();
            } else {
                PutMappingResponse response = client.admin().indices().preparePutMapping(esClientFactory.getIndexs(indexName)).setType(indexType).setSource(contentBuilder).get();
                return response.isAcknowledged();
            }
        } catch (IOException e) {
            logger.error("创建失败,IOException", e);
            return false;
        }
    }

    /**
     * 获取mapping
     *
     * @param indexName
     * @param indexType
     *
     * @return
     */
    public ActionResponse getMapping(String indexName, String indexType) {
        TransportClient client = esClientFactory.getClient();
        GetMappingsResponse response = client.admin().indices().prepareGetMappings(esClientFactory.getIndexs(indexName)).setTypes(indexType).get();
        return response;
    }

    /**
     * 删除type
     *
     * @param indexName
     * @param indexType
     *
     * @return
     */
    public boolean deleteType(String indexName, String indexType) {
        TransportClient client = esClientFactory.getClient();
        try {
            //client.prepareDelete(esClientFactory.getIndexs(indexName)).setType(indexType).get();
            return true;
        } catch (Exception e) {
            logger.error("删除type失败", e);
            return false;
        }
    }

    /**
     * 检查索引是否存在
     * @param indexs
     * @return
     */
    public boolean checkIndex(String ...indexs) {
        TransportClient client = esClientFactory.getClient();
        // 如果存在返回true
        if (client.admin().indices().prepareExists(esClientFactory.getIndexs(indexs)).get().isExists()) {
            return true;
        }
        return false;
    }

    /**
     * 检查type是否存在
     * @param indexName
     * @param typeNames
     * @return
     */
    public boolean checkIndexTypes(String indexName,String ...typeNames) {
        TransportClient client = esClientFactory.getClient();
        // 如果存在返回true
        if (client.admin().indices().prepareTypesExists(esClientFactory.getIndexs(indexName)).setTypes(typeNames).get().isExists()){
            return true;
        }
        return false;
    }
    public AnalyzeResponse analyze(String indexName,String keyWords,AnalyzeConstant.IKAnalyze iKAnalyze) {
        TransportClient client = esClientFactory.getClient();
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        return indicesAdminClient.prepareAnalyze(esClientFactory.getIndexs(indexName),keyWords).setAnalyzer(iKAnalyze.getCode()).get();
    }
    public EshClientFactory getEsClientFactory() {
        return esClientFactory;
    }

    public void setEsClientFactory(EshClientFactory esClientFactory) {
        this.esClientFactory = esClientFactory;
    }

}
