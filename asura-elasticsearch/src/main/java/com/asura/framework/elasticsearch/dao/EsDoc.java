package com.asura.framework.elasticsearch.dao;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.util.Check;
import com.asura.framework.elasticsearch.client.EshClientFactory;
import com.asura.framework.elasticsearch.exception.EsDocException;
import com.asura.framework.elasticsearch.po.DocPo;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
 * @date 2016/8/29 21:44
 * @since 1.0
 */
public class EsDoc implements  IEsDocDao{
    private static Logger logger = LoggerFactory.getLogger(EsDoc.class);
     private static final int len = 1000;//批量
    private EshClientFactory esClientFactory;

  /*  public EsDoc(){
        esClientFactory=new EshClientFactory();
        esClientFactory.init();
    }*/
    /**
     * 修改文档
     * @param indexName
     * @param indexType
     * @param id
     * @param obj
     * @return
     */
    public boolean  updateDocument(String indexName, String indexType,String id,Object obj) {

        TransportClient client = esClientFactory.getClient();
        client.prepareUpdate(esClientFactory.getIndexs(indexName),indexType,id).setDoc(JSON.toJSONString(obj)).execute().actionGet();
        /*IndexResponse indexResponse = client.prepareIndex(indexName,indexType).setId(id).
                setSource(JSON.toJSONString(obj)).execute().actionGet();*/
        return true;


    }

    /**
     * 修改文档
     *
     * @param indexName
     * @param indexType
     * @param id
     * @param
     *
     * @return
     */
    public boolean updateFileds(String indexName, String indexType, String id, Map<String, Object> fieldValues) {
        TransportClient client = esClientFactory.getClient();
        client.prepareUpdate(esClientFactory.getIndexs(indexName),indexType,id).setDoc(JSON.toJSONString(fieldValues)).execute().actionGet();
        return true;
    }


    /**
     * 新增文档 es自动生成id
     * @param indexName
     * @param indexType
     * @param obj
     * @return
     */
    public boolean  createDocument(String indexName, String indexType,Object obj) {
        TransportClient client = esClientFactory.getClient();
        IndexResponse indexResponse = client.prepareIndex(esClientFactory.getIndexs(indexName),indexType).
                setSource(JSON.toJSONString(obj)).execute().actionGet();
        return indexResponse.isCreated();
    }
    /**
     * 保存文档
     * @param indexName
     * @param indexType
     * @param obj
     * @return
     */
    public boolean  saveOrUpdateDocument(String indexName, String indexType,String id,Object obj) {
        TransportClient client = esClientFactory.getClient();
        if(Check.NuNStrStrict(id)){
            logger.error("保存文档失败","id为空，请传id");
            return false;
        }
        IndexResponse indexResponse = client.prepareIndex(esClientFactory.getIndexs(indexName),indexType).setId(id).
                    setSource(JSON.toJSONString(obj)).execute().actionGet();
        return true;
    }
    /**
     * 批量save
     * @param indexName
     * @param indexType
     * @param list
     * @return
     */
    private  BulkResponse bulkSaveOrUpdate(String indexName, String indexType, List<DocPo> list){
        TransportClient client = esClientFactory.getClient();
        BulkResponse bulkResponse=null;
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for(int i=0;i<list.size();i++){
            bulkRequest.add(new IndexRequest(esClientFactory.getIndexs(indexName), indexType,list.get(i).getId()).source(JSON.toJSONString(list.get(i).getObj())));
        }
        bulkResponse = bulkRequest.get();
        // 处理错误信息
        handBulkResponseException(bulkResponse);
        return bulkResponse;
    }
    //处理错误西悉尼
    private void handBulkResponseException(BulkResponse bulkResponse){
        if (bulkResponse.hasFailures()) {
            long count = 0L;
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                logger.error("发生错误的 索引id为 : "+bulkItemResponse.getId()+" ，错误信息为："+ bulkItemResponse.getFailureMessage());
                count++;
            }
            logger.error("发生错误的 总个数为 : "+count);
        }
    }
    /**
     * 批量del
     * @param indexName
     * @param indexType
     * @param
     * @return
     */
    private  BulkResponse bulkDelete(String indexName, String indexType, List<String> ids){
        TransportClient client = esClientFactory.getClient();
        BulkResponse bulkResponse=null;
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for(int i=0;i<ids.size();i++){
            bulkRequest.add(new DeleteRequest(esClientFactory.getIndexs(indexName), indexType,ids.get(i) ));
        }
        bulkResponse = bulkRequest.get();
        // 处理错误信息
        handBulkResponseException(bulkResponse);
        return bulkResponse;
    }
    /**
     * 批量saves
     * @param indexName
     * @param indexType
     * @param list
     * @return
     */
    public  List<BulkResponse> bulkSaveOrUpdates(String indexName, String indexType, List<DocPo> list,int len)  throws   EsDocException{
        List<BulkResponse> bulkResponses=null;
        if(Check.NuNCollection(list)||0==len){
            throw  new EsDocException("集合为空 后者 批处理量为0");
        }
        boolean flag=true;
        for(int i=0;i<list.size();i++){
            if(Check.NuNStrStrict(list.get(i).getId())){
                flag=false;
                break;
            }
        }
        if(!flag){
            throw  new EsDocException("id 不能为空");
        }
        bulkResponses=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            BulkResponse bulkResponse=null;
            int end = len + i -1;
            if(end>=list.size()){
                bulkResponse=bulkSaveOrUpdate(indexName,indexType,list.subList(i,list.size()));
            }else{
                bulkResponse= bulkSaveOrUpdate(indexName,indexType,list.subList(i,end+1));
            }
            i = end;
            bulkResponses.add(bulkResponse);
        }
        return bulkResponses;
    }
    /**
     * 批量saves
     * @param indexName
     * @param indexType
     * @param list
     * @return
     */
    public  List<BulkResponse> bulkSaveOrUpdates(String indexName, String indexType, List<DocPo> list) throws   EsDocException{
        return bulkSaveOrUpdates(indexName,  indexType, list,len);
    }
    /**
     * delete
     * @param indexName
     * @param indexType
     * @param list
     * @return
     */
    public  List<BulkResponse> bulkDeletes(String indexName, String indexType, List<String> list,int len)  throws   EsDocException{
        List<BulkResponse> bulkResponses=null;
        if(Check.NuNCollection(list)||0==len){
            throw  new EsDocException("集合为空 后者 批处理量为0");
        }
        boolean flag=true;
        for(int i=0;i<list.size();i++){
            if(Check.NuNStrStrict(list.get(i))){
                flag=false;
                break;
            }
        }
        if(!flag){
            throw  new EsDocException("id 不能为空");
        }
        bulkResponses=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            BulkResponse bulkResponse=null;
            int end = len + i -1;
            if(end>=list.size()){
                bulkResponse=bulkDelete(indexName,indexType,list.subList(i,list.size()));
            }else{
                bulkResponse= bulkDelete(indexName,indexType,list.subList(i,end+1));
            }
            i = end;
            bulkResponses.add(bulkResponse);
        }
        return bulkResponses;
    }
    /**
     * 批量delete
     * @param indexName
     * @param indexType
     * @param list
     * @return
     */
    public  List<BulkResponse> bulkDeletes(String indexName, String indexType, List<String> list) throws   EsDocException{
        return bulkDeletes(indexName,  indexType, list,len);
    }
    /**
     * 删除文档
     * @param indexName
     * @param indexType
     * @param id
     * @return
     */
    public  boolean deleteDocument(String indexName, String indexType, String id) {
        TransportClient client = esClientFactory.getClient();
        DeleteResponse indexResponse=client.prepareDelete(esClientFactory.getIndexs(indexName), indexType, id).get();
        return indexResponse.isFound();
    }

    public EshClientFactory getEsClientFactory() {
        return esClientFactory;
    }

    public void setEsClientFactory(EshClientFactory esClientFactory) {
        this.esClientFactory = esClientFactory;
    }
}
