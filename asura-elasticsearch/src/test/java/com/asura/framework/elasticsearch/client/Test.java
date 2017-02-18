package com.asura.framework.elasticsearch.client;

import com.vividsolutions.jts.geom.Coordinate;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

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
 * @date 2016/8/27 18:50
 * @since 1.0
 */
public class Test {
    public static void main(String[] args) {
       /* QueryBuilder qb1 = QueryBuilders.matchQuery("a","b");
        System.out.println(qb1.toString());*/
        String json="{\"query\":{\"match_all\":{}},\"filter\":{\"geo_shape\":{\"geometry\":{\"relation\":\"CONTAINS\",\"shape\":{\"coordinates\":[116.402257,39.914548],\"type\":\"point\"}}}}}";
        QueryBuilder qb= QueryBuilders.matchAllQuery();
        //System.out.println(qb.toString());
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(qb);
       // System.out.println(searchSourceBuilder.toString());
        ShapeBuilder shapeBuilder= ShapeBuilder.newPoint(new Coordinate(116.402257,39.914548));
        QueryBuilder qb2= QueryBuilders.geoShapeQuery("geometry",shapeBuilder, ShapeRelation.CONTAINS);
        System.out.println(qb2.toString());

        //searchSourceBuilder.postFilter(qb2);
        QueryBuilder qb3= QueryBuilders.boolQuery().must(qb).filter(qb2);
        searchSourceBuilder.query(qb3);
        System.out.println(qb3.toString());
        System.out.println(searchSourceBuilder.toString());
        QueryBuilder qb4= QueryBuilders.boolQuery().must(qb).must(qb2);
        System.out.println(qb4.toString());


        SortBuilder sort= SortBuilders.geoDistanceSort("pin.location") .point(40, -70).
                unit(DistanceUnit.fromString(DistanceUnit.KILOMETERS.toString())).order(SortOrder.DESC);
      /*  QueryBuilder qb5 = QueryBuilders.geoDistanceQuery("pin.location")
                .point(40, -70)
                .distance(400,  DistanceUnit.fromString(DistanceUnit.KILOMETERS.toString()))
                .geoDistance(GeoDistance.ARC);
                 System.out.println(qb5.toString());
                */
        searchSourceBuilder.sort(sort);
        System.out.println(searchSourceBuilder.toString());
        //QueryBuilder qb3=QueryBuilders.filteredQuery(null,qb2);
        //QueryBuilder qb4=QueryBuilders.filteredQuery(qb,qb2);
        //searchSourceBuilder.query(qb3.toString());
       // searchSourceBuilder.query(qb4);
       // System.out.println(qb4.toString());
        //System.out.println(searchSourceBuilder.toString());

        // System.out.println(qb.toString());
       /* QueryBuilder qb2 = QueryBuilders.geoBoundingBoxQuery("pin.location")
                .topLeft(40.73, -74.1)
                .bottomRight(40.717, -73.99);
        //String  strstr= JSON.toJSONString(qb2);
        System.out.println(qb2.toString());
        System.out.println("1111111");*/
    }
}
