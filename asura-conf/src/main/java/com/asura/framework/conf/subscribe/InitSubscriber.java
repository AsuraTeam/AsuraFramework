/**
 * @FileName: InitSubscriber.java
 * @Package com.asura.framework.conf.subscribe
 * 
 * @author zhangshaobin
 * @created 2013-6-26 上午10:23:47
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.conf.subscribe;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * <p>初始化订阅者</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class InitSubscriber implements BeanFactoryPostProcessor {

	private InitSubscriber() {
	}

	/**
	 * 
	 * 初始化
	 *
	 * @author zhangshaobin
	 * @created 2013-6-26 上午10:24:10
	 *
	 */
	public void init() {
		ConfigSubscriber.getInstance();
		AsuraSubAnnotationProcessor.getInstance(); // 必须在ConfigSubscriber.getInstance()之后
		InitFileProperties.getInstance();
	}

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        init();
    }
}
