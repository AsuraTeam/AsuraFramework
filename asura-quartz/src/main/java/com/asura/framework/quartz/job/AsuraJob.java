/**
 * @FileName: AsuraJob.java
 * @Package com.asura.framework.quartz.job
 * 
 * @author zhangshaobin
 * @created 2014年12月10日 下午9:22:54
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.conf.subscribe.ConfigSubscriber;

/**
 * <p>可以控制空跑的定时任务</p>
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
public abstract class AsuraJob implements StatefulJob {
	
	private static final Logger logger = LoggerFactory.getLogger(AsuraJob.class);

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (quartzKP()) {
			logger.info(context.getJobDetail().getJobClass().getName() + " 定时任务空跑.....");
		} else {
			logger.info("JOB: " + context.getJobDetail().getJobClass().getName()+" start .....");
			long start = System.currentTimeMillis();
			run(context);
			long end = System.currentTimeMillis();
			logger.info("JOB: " + context.getJobDetail().getJobClass().getName()+" end ..... 耗时：" + (end - start) +" 毫秒");
		}
	}

	public abstract void run(JobExecutionContext context);

	/**
	 * 
	 * 是否空跑定时任务
	 *
	 * @author zhangshaobin
	 * @created 2014年12月10日 下午9:32:07
	 *
	 * @return true 空跑   ； false 不空跑
	 */
	private boolean quartzKP() {
		String type = EnumSysConfig.asura_quartzKP.getType();
		String code = EnumSysConfig.asura_quartzKP.getCode();
		String value = ConfigSubscriber.getInstance().getConfigValue(type, code);
		if (value == null) {
			value = EnumSysConfig.asura_quartzKP.getDefaultValue();
		}
		if ("on".equals(value)) { // 空跑
			return true;
		} else {
			return false;
		}
	}

}
