package com.asura.framework.elasticsearch.dao;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;

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
 * @date 2016/8/30 20:25
 * @since 1.0
 */
public class EsQueryDo {
    /**
     * ES索引名
     */
    String indexName;
    /**
     * ES TYPE名（表名)
     */
    String typeName;

    private byte searchType;


    private QueryBuilder queryBuilder;

    private List<AbstractAggregationBuilder> aggregationBuilders;

    private List<SortBuilder> sortBuilders;
    /**
     * 取值的起始位置，默认为0
     */
    int fromIndex;
    /**
     * 返回数据数量，默认为100，最大不能超过100000
     */
    int size=100;

    boolean isExplain;

    private boolean isHighLigth ;

    private String highLigthFields;

    private String highLigthPreTag = "<span style=\"color:red\">";

    private String highLigthPostTag = "</span>";

    private String aggregationFields;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public QueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public List<AbstractAggregationBuilder> getAggregationBuilders() {
        return aggregationBuilders;
    }

    public void setAggregationBuilders(List<AbstractAggregationBuilder> aggregationBuilders) {
        this.aggregationBuilders = aggregationBuilders;
    }

    public List<SortBuilder> getSortBuilders() {
        return sortBuilders;
    }

    public void setSortBuilders(List<SortBuilder> sortBuilders) {
        this.sortBuilders = sortBuilders;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte getSearchType() {
        return searchType;
    }

    public void setSearchType(byte searchType) {
        this.searchType = searchType;
    }

    public boolean isExplain() {
        return isExplain;
    }

    public void setExplain(boolean explain) {
        isExplain = explain;
    }

    public boolean isHighLigth() {
        return isHighLigth;
    }

    public void setHighLigth(boolean highLigth) {
        isHighLigth = highLigth;
    }
    public String getHighLigthFields () {
        return highLigthFields;
    }

    public String[] highLigthFields () {
        return highLigthFields.split(",");
    }

    public void setHighLigthFields (String highLigthFields) {
        this.highLigthFields = highLigthFields;
    }

    public String getHighLigthPreTag() {
        return highLigthPreTag;
    }

    public void setHighLigthPreTag(String highLigthPreTag) {
        this.highLigthPreTag = highLigthPreTag;
    }

    public String getHighLigthPostTag() {
        return highLigthPostTag;
    }

    public void setHighLigthPostTag(String highLigthPostTag) {
        this.highLigthPostTag = highLigthPostTag;
    }

    public String getAggregationFields () {
        return aggregationFields;
    }

    public String[] aggregationFields () {
        if (aggregationFields != null) {
            return aggregationFields.split(",");
        }
        return null;
    }

    public void setAggregationFields (String aggregationFields) {
        this.aggregationFields = aggregationFields;
    }
}
