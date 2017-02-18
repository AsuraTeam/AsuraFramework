/**
 * @FileName: AsuraJob.java
 * @Package com.asura.test.jobs
 * 
 * @author zhangshaobin
 * @created 2014年12月10日 下午6:11:16
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.test.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

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
public abstract class AsuraJob implements StatefulJob {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("AsuraJob。。。" + new Date());
		boolean flag = true;
		if (flag) {
			return;
		} else {
			run(flag);
		}

	}

	public abstract void run(boolean flag);

}
