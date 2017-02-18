package com.asura.test;

import com.asura.framework.conf.subscribe.ConfigSubscriber;
import com.asura.test.pojo.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
 * @date 2016/9/2 10:02
 * @since 1.0
 */
@ContextConfiguration(locations = {"classpath:/META-INF/spring/springTest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class T_EmployeeService {
    final static Logger logger = LoggerFactory.getLogger(T_EmployeeService.class);

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testUrl() throws Exception {
        String str = employeeService.getUrl();
        logger.info(str);
    }

    @Test
    public void test_ZkConfig(){
        String s1 = T_ZkConfigContext.getOldZrkInfoByTokenUrl();
        logger.info(s1);
        String s2 = (String) T_ZkConfigContext.getNewZrkInfoByTokenUrl();
        logger.info(s2);
    }

    @Test
    public void test_properties(){
        String oracleDriverClassName = ConfigSubscriber.getInstance().getConfigValue("asura.jdbc.oracleDriverClassName");
        logger.info(oracleDriverClassName);
    }
}
