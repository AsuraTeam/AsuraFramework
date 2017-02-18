package com.asura.test.pojo;


import com.asura.framework.conf.subscribe.AsuraSubField;
import org.springframework.stereotype.Component;

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
 * @date 2016/9/2 10:01
 * @since 1.0
 */
@Component
public class EmployeeService {
    private String url;

    @AsuraSubField(appName = "asura", type = "test_type", code = "test_code", defaultValue = "c")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
