/**
 * @FileName: ApplicationContext.java
 * @Package com.sfbest.framework.context
 * 
 * @author zhangshaobin
 * @created 2013-3-7 上午8:22:03
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.base.context;

import org.springframework.context.support.AbstractApplicationContext;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.spring.SpringContainer;

/**
 * <p>TODO</p>
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
public class ApplicationContext {
	
	/**
	 * 
	 * 获取Spring上下文信息
	 *
	 * @author zhangshaobin
	 * @created 2013-3-7 上午9:35:00
	 *
	 * @return
	 */
	public static AbstractApplicationContext getContext() {
		return SpringContainer.getContext();
	}
	
	/**
	 * 
	 * 获取当前应用名称
	 *
	 * @author zhangshaobin
	 * @created 2013-4-9 下午12:30:10
	 *
	 * @return	应用名称
	 */
	public static String getApplicationName() {
		return ConfigUtils.getProperty("dubbo.application.name");
	}
	
	/**
	 * 
	 * 获取注册中心地址
	 *
	 * @author zhangshaobin
	 * @created 2013-4-9 下午12:32:38
	 *
	 * @return
	 */
	public static String getRegistryAddress() {
		return ConfigUtils.getProperty("dubbo.registry.address");
	}
	
	/**
	 * 
	 * 当前应用是否为服务提供者
	 *
	 * @author zhangshaobin
	 * @created 2013-3-8 下午5:57:34
	 *
	 * @return
	 */
	public static boolean isProviderSide() {
		return getApplicationName().endsWith("Provider");
	}
	
	/**
	 * 
	 * 当前应用是否为服务消费者
	 *
	 * @author zhangshaobin
	 * @created 2013-3-8 下午5:57:51
	 *
	 * @return
	 */
	public static boolean isConsumerSide() {
		return getApplicationName().endsWith("Consumer");
	}

}
