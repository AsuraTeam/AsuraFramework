package com.asura.framework.elasticsearch.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.elasticsearch.EsResponse;
import com.asura.framework.elasticsearch.client.EshClientFactory;
import com.asura.framework.elasticsearch.exception.EsException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.threadpool.ThreadPoolStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
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
public class EsQuery implements  IEsQueryDao{
    private static Logger logger = LoggerFactory.getLogger(EsQuery.class);
    private EshClientFactory esClientFactory;//ES 客户端工厂类
   /* private EshClientFactory esClientFactory;

    public EsQuery(){
        esClientFactory=new EshClientFactory();
        esClientFactory.init();
    }*/
   public SearchResponse query(SearchSourceBuilder searchSourceBuilder, String indexName, String... typeNames) throws EsException {
        validationEsQuery(indexName,typeNames);
        SearchResponse response=esClientFactory.getClient().prepareSearch(esClientFactory.getIndexs(indexName)).setTypes(typeNames).setSource(searchSourceBuilder.toString()).execute().actionGet();
       return response;
    }
    public EsResponse queryByEsQueryDo(EsQueryDo esQueryObj) throws EsException {
        validationEsQuery(esQueryObj.getIndexName(),esQueryObj.getTypeName());
        //创建ES查询Request对象
        SearchRequestBuilder esSearch=buildSearchRequest (esQueryObj);
        //执行查询
        SearchResponse response =esSearch.execute().actionGet();
        JSONObject resObj = new JSONObject();
        //获取facet结果
        if(!Check.NuNObject(esQueryObj.aggregationFields())){
            parseAggregationResult(response, esQueryObj.aggregationFields(), resObj);
        }
        //1、获取搜索的文档结果
        SearchHits searchHits = response.getHits();
        if (searchHits == null || searchHits.getTotalHits() == 0) {
            return EsResponse.responseOK(null);
        }
        SearchHit[] hits = searchHits.getHits();
        resObj.put("total", searchHits.getTotalHits());
        //1.1、获取搜索结果
        parseSearchResult(hits, esQueryObj.isHighLigth(), esQueryObj, resObj);
        return EsResponse.responseOK(resObj);
    }
    private SearchRequestBuilder buildSearchRequest (EsQueryDo esQueryObj) throws  EsException {
        if (Check.NuNStrStrict(esClientFactory.getIndexs(esQueryObj.getIndexName()))) {
            throw new EsException("没有指定要搜索的索引名称(indexName)");
        }
        for (ThreadPoolStats.Stats stats : esClientFactory.getClient().threadPool().stats()) {
            logger.info(JSON.toJSONString(stats));
        }
        //加载要搜索索引
        SearchRequestBuilder searchRequestBuilder = esClientFactory.getClient().prepareSearch(esClientFactory.getIndexs(esQueryObj.getIndexName()));
        //由spring从配置加载要搜索的index的类型
        searchRequestBuilder.setTypes(esQueryObj.getTypeName());
        //由spring从配置加载要搜索的类型
        searchRequestBuilder.setSearchType(SearchType.fromId(esQueryObj.getSearchType()));
        //查询可以为null
        searchRequestBuilder.setQuery(esQueryObj.getQueryBuilder());

        if (!Check.NuNCollection(esQueryObj.getSortBuilders())) {
            for (SortBuilder sortBuilder : esQueryObj.getSortBuilders()) {
                searchRequestBuilder.addSort(sortBuilder);
            }
        }
        if (!Check.NuNCollection(esQueryObj.getAggregationBuilders())) {
            for (AbstractAggregationBuilder aggregationBuilder : esQueryObj.getAggregationBuilders()) {
                searchRequestBuilder.addAggregation(aggregationBuilder);
            }

        }
        //设置高亮域
       if (esQueryObj.isHighLigth()) {
            if (!Check.NuNObject(esQueryObj.highLigthFields())) {
                for (String hlFieldName : esQueryObj.highLigthFields()) {
                    searchRequestBuilder.addHighlightedField(hlFieldName).setHighlighterPreTags(esQueryObj.getHighLigthPreTag())
                            .setHighlighterPostTags(esQueryObj.getHighLigthPostTag());
                }
            }
        }
        //分页
        searchRequestBuilder.setFrom(esQueryObj.getFromIndex()).setSize(esQueryObj.getSize());
        searchRequestBuilder.setExplain(esQueryObj.isExplain());
        return searchRequestBuilder;
    }

    private void parseSearchResult (SearchHit[] hits, boolean isHighLigth, EsQueryDo esQueryObj, JSONObject resObj) {
        List<JSONObject> searchRes = new ArrayList<JSONObject>();
        for (int i = 0; i < hits.length; i++) {
            SearchHit hit = hits[i];
            //将文档中的每一个对象转换json串值
            JSONObject hitJson=new  JSONObject();
            hitJson.put("id",hit.getId());
            hitJson.put("score",hit.getScore());
            JSONObject json = JSON.parseObject(hit.getSourceAsString());
            //如果高亮设置为true
            if (isHighLigth) {
                if (!Check.NuNObject(esQueryObj.highLigthFields())) {
                    for (String hlFieldName : esQueryObj.highLigthFields()) {
                        highLightResult(json, hit, hlFieldName);
                    }
                }

            }
            hitJson.put("source",json);
            searchRes.add(hitJson);
        }
        resObj.put("result", searchRes);

    }

    private void parseAggregationResult (SearchResponse response, String[] aggreFields, JSONObject resObj) {
        List<Map<String, Object>> aggrs = new ArrayList<Map<String, Object>>();
        for (String aggreField : aggreFields) {
            Terms terms = response.getAggregations().get(aggreField);
            if (terms != null) {
                Map<String, Object> maps = new HashMap<String, Object>();
                for (Terms.Bucket bucket : terms.getBuckets()) {
                    maps.put((String)bucket.getKey(), bucket.getDocCount());
                }
                JSONObject jo = new JSONObject();
                jo.put(aggreField, maps);
                aggrs.add(jo);
            }
        }
        resObj.put("aggregations", aggrs);
    }

    /**
     * 对搜索命中结果做高亮
     *
     * @param json
     * @param hit
     * @param highLigthFieldName
     */
    private void highLightResult (JSONObject json, SearchHit hit, String highLigthFieldName) {
        //获取对应的高亮域
        Map<String, HighlightField> result = hit.highlightFields();
        //从设定的高亮域中取得指定域
        HighlightField hlField = result.get(highLigthFieldName);
        if (Check.NuNObj(hlField)) {
            return;
        }
        //取得定义的高亮标签
        Text[] hlTexts = hlField.fragments();
        if (Check.NuNObject(hlTexts)) {
            return;
        }
        //为title串值增加自定义的高亮标签
        StringBuffer hlTextsFiled = new StringBuffer();
        for (Text text : hlTexts) {
            hlTextsFiled.append(text);
        }
        //如果高亮域内有fragments 反回的数据不为空字符串
        if (!Check.NuNStrStrict(hlTextsFiled.toString())) {
            json.put(highLigthFieldName, hlTextsFiled);
        }
    }
   /**
     * 验证ES查询对象
     * @param
     * @throws Exception
     */
    private void validationEsQuery(String  indexName,String... typeNames) throws EsException{
        boolean flag=true;
        for (String typeName : typeNames) {
            if(Check.NuNStr(typeName)){
                flag=false;
                break;
            }
        }
        if(Check.NuNStr(esClientFactory.getIndexs(indexName)) ||!flag){
            throw  new EsException("please check indexName and typeName");
        }
        if (!esClientFactory.getClient().admin().indices().prepareExists(esClientFactory.getIndexs(indexName)).get().isExists()) {
            throw  new EsException("indexName is not exist in es server");
        }
        if (!esClientFactory.getClient().admin().indices().prepareTypesExists(esClientFactory.getIndexs(indexName)).setTypes(typeNames).get().isExists()) {
            throw  new EsException("typeName is not exist in es server");
        }
    }

    public EshClientFactory getEsClientFactory() {
        return esClientFactory;
    }

    public void setEsClientFactory(EshClientFactory esClientFactory) {
        this.esClientFactory = esClientFactory;
    }
}
