/**
 * @FileName: DB2.java
 * @Package com.asura.test
 * 
 * @author zhangshaobin
 * @created 2014年12月2日 下午7:16:52
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.test;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

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
public class DB3 {
	public static void main(String[] args) throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory(
				"E:\\git-working\\asura-framework\\asura\\asura-dubbo\\src\\test\\java\\com\\asura\\test\\quartz.properties");
		Scheduler sched = sf.getScheduler();
		sched.start();

	}

}
