package com.asura.framework.conf.subscribe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
 * @date 2016/8/10 15:35
 * @since 1.0
 */
@Target({ ElementType.METHOD,ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface  AsuraSubField {
     String appName();
     String type();
     String code();
     String defaultValue() default "";

}
