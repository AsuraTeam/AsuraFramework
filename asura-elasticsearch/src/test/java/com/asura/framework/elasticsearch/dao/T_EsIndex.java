package com.asura.framework.elasticsearch.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
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
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/8/31 14:23
 * @since 1.0
 */
@ContextConfiguration(locations = {"classpath:/META-INF/spring/application-elasticsearch.xml", "classpath:/META-INF/spring/application-config-subscriber.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class T_EsIndex {

    @Resource(name = "esIndex")
    private IEsIndexDao esIndexDao;

    @Test
    public void t_createType() throws Exception {
        Map<String, String> mappingMap = new HashMap<>();
        mappingMap.put("cid", "type:integer");
        mappingMap.put("logicCode", "type:string");
        mappingMap.put("employeeCode", "type:string");
        mappingMap.put("transactionType", "type:string");
        mappingMap.put("orderCode", "type:string");
        mappingMap.put("startTime", "type:long");
        mappingMap.put("endTime", "type:long");
        mappingMap.put("dispatchStartTime", "type:long");
        mappingMap.put("dispatchEndTime", "type:long");
        mappingMap.put("supplierCode", "type:string");
        mappingMap.put("parentTransactionCode", "type:string");
        mappingMap.put("startPoint", "type:geo_point");
        mappingMap.put("endPoint", "type:geo_point");
        mappingMap.put("dispatchStartPoint", "type:geo_point");
        mappingMap.put("dispatchEndPoint", "type:geo_point");
        esIndexDao.createTypeOnMapping("movetransaction", "employeetransaction", mappingMap);
    }

    @Test
    public void t_createIndex(){
        esIndexDao.createIndex("movetransaction");
    }
}
