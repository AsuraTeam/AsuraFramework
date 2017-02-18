/**
 * @FileName: SfbestSchedulerContainer.java
 * @Package com.asura.framework.dubbo.container
 * 
 * @author zhangshaobin
 * @created 2013-3-7 上午9:00:08
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dubbo.container;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.container.Container;
import com.asura.framework.base.exception.BusinessException;

/**
 * <p>启动Quartz，在dubbo.properties的dubbo.container项最后加入“scheduler”</p>
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
public class SfbestSchedulerContainer implements Container {

	private Logger logger = LoggerFactory.getLogger(SfbestSchedulerContainer.class);
	
	/**
	 * Scheduler
	 */
	private Scheduler scheduler;
	
	/**
	 * 
	 * 启动QuartzScheduler
	 *
	 * @author zhangshaobin
	 * @created 2013-1-4 下午4:11:50
	 *
	 */
	public void start() throws BusinessException {
		try {
			SchedulerFactory sf = new StdSchedulerFactory("quartz.properties");
			scheduler = sf.getScheduler();
			scheduler.start();
			logger.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Quartz started!");
		} catch (SchedulerException e) {
			logger.error("启动Quartz出错:" + e.getMessage(), e.getCause());
			throw new BusinessException(e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 
	 * 停止QuartzScheduler
	 *
	 * @author zhangshaobin
	 * @created 2013-1-4 下午5:18:15
	 *
	 * @throws BusinessException
	 */
	public void stop() throws BusinessException {
		if (null != scheduler) {
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				logger.error("停止Quartz出错:" + e.getMessage(), e.getCause());
				throw new BusinessException(e.getMessage(), e.getCause());
			}
		}
	}

}
