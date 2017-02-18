package com.asura.framework.elasticsearch.client;

import com.alibaba.fastjson.JSON;
import com.asura.framework.elasticsearch.EsResponse;
import com.asura.framework.elasticsearch.dao.EsDoc;
import com.asura.framework.elasticsearch.dao.EsQueryDo;
import com.asura.framework.elasticsearch.exception.EsException;
import com.vividsolutions.jts.geom.Coordinate;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
 * @date 2016/8/31 11:37
 * @since 1.0
 */
//@ContextConfiguration(locations = {"classpath:/META-INF/spring/application-elasticsearch.xml"})
@ContextConfiguration(locations = "classpath:/META-INF/spring/application-elasticsearch.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class T_IEsQueryDao {
    private static Logger logger = LoggerFactory.getLogger(EsDoc.class);
    @Resource(name = "esQuery")
    private com.asura.framework.elasticsearch.dao.IEsQueryDao iEsQueryDao;

    @org.junit.Test(expected = EsException.class)
    public void t_queryByEsQueryDo()throws Exception{
        EsQueryDo esQueryObj=new EsQueryDo();
        esQueryObj.setIndexName("test_db");
        esQueryObj.setTypeName("test_user");
        EsResponse er= iEsQueryDao.queryByEsQueryDo(esQueryObj);
        logger.info(JSON.toJSONString(er));
    }
    @org.junit.Test
    public void t_queryByEsQueryDo2()throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder qb = QueryBuilders.matchAllQuery();
        searchSourceBuilder.query(qb);
        ShapeBuilder shapeBuilder = ShapeBuilder.newPoint(new Coordinate(116.402257, 39.914548));
        QueryBuilder qb2 = QueryBuilders.geoShapeQuery("geometry", shapeBuilder, ShapeRelation.CONTAINS);

        QueryBuilder qb3 = QueryBuilders.boolQuery().must(qb).filter(qb2);
        searchSourceBuilder.query(qb3);
       iEsQueryDao.query(searchSourceBuilder,"twitter","user","user2");
        /*logger.info(JSON.toJSONString(es));
        Assert.assertNotEquals("1", es.getStatus());*/
    }

}
