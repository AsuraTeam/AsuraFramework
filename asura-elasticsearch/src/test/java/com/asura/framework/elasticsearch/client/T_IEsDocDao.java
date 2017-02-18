package com.asura.framework.elasticsearch.client;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.elasticsearch.po.DocPo;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
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
 * @date 2016/8/31 15:24
 * @since 1.0
 */
@ContextConfiguration(locations = "classpath:/META-INF/spring/application-elasticsearch.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class T_IEsDocDao {
    @Resource(name = "esDoc")
    private com.asura.framework.elasticsearch.dao.IEsDocDao esDocDao;

    @org.junit.Test
    public void t_bulkSave()throws Exception{

        List<DocPo> list=new ArrayList<>();
        DocPo doc=new DocPo();
        //doc.setId("1");
        Person p=new Person();
        p.setId("1");
        p.setName("a");
        doc.setObj(p);
        DocPo doc2=new DocPo();
        doc2.setId("aaaaaaaaaaaaaaaaa");
        doc2.setObj(p);
        list.add(doc);
        list.add(doc2);
        list.add(doc);
        list.add(doc);
        esDocDao.bulkSaveOrUpdates("jn","test",list,3);
    }
    @org.junit.Test
    public void t_saveOrUpdateDocument()throws Exception{
        Person p=new Person();
        p.setId("a");
        p.setName("b");
        esDocDao.saveOrUpdateDocument("jn","test","aaa",p);
    }
    @org.junit.Test
    public void t_updateDocument()throws Exception{
        Person p=new Person();
        p.setId("aaaaaa");
        p.setName("bbbbbbbb");
        esDocDao.updateDocument("jn","test","rm",p);
    }
    @org.junit.Test
    public void t_deleteDocument()throws Exception{
        Person p=new Person();
        p.setId("aaaaaa");
        p.setName("bbbbbbbb");
        esDocDao.deleteDocument("jn","test",null);
    }
    @org.junit.Test
    public void t_bulkDeletes()throws Exception{
        List<String> list=new ArrayList<>();
        list.add("sun");
        list.add("aaa");
        list.add("dddd");
        esDocDao.bulkDeletes("jn","test",list);
    }

    @org.junit.Test
    public void t_updateFileds() throws Exception {
        String str = "{\"cityCode\":\"310000\",\"isDetele\":null,\"logicCode\":\"8a9e9bbf57037b190157037b19790001\",\"productionTypeId\":\"null\",\"serviceAreaCode\":\"DDDDDDD\"}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        esDocDao.updateFileds("clean_supplier", "clean_supplier_shape", "8a9e9bbf57037b190157037b19790001", (Map<String, Object>) jsonObject);
    }
}
