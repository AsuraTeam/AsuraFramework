/**
 * @FileName: T.java
 * @Package com.asura.test18
 * 
 * @author zhangshaobin
 * @created 2014年12月2日 下午11:30:38
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.test;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.asura.test.jobs.C;

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
public class T {

	private static String addTrigger(String triggerName, String triggerGroup, String cronExpression,
			Class<? extends Job> jobClass) throws SchedulerException, ParseException {
		org.quartz.Trigger trigger = SchedulerExecutor.getScheduler().getTrigger(triggerName, triggerGroup);
		if (trigger == null) {// 判断是否有重复的触发器
			JobDetail jobDetail = new JobDetail(triggerName, Scheduler.DEFAULT_GROUP, jobClass);
			JobDataMap map = new JobDataMap();
			map.put("msg", "Your remotely added job has executed!");
			jobDetail.setJobDataMap(map);
			CronTrigger cronTrigger = null;
			cronTrigger = new CronTrigger(triggerName, triggerGroup, jobDetail.getName(), jobDetail.getGroup(),
					cronExpression);
			SchedulerExecutor.getScheduler().addJob(jobDetail, true);
			SchedulerExecutor.getScheduler().scheduleJob(cronTrigger);
			SchedulerExecutor.getScheduler().rescheduleJob(cronTrigger.getName(), cronTrigger.getGroup(), cronTrigger);
			return triggerName;
		} else {
			return null;
		}

	}

	private static boolean deleteTrigger(String triggerName, String triggerGroup) {
		try {
			SchedulerExecutor.getScheduler().pauseTrigger(triggerName, triggerGroup);// 停止触发器
			return SchedulerExecutor.getScheduler().unscheduleJob(triggerName, triggerGroup);// 移除触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws SchedulerException, ParseException {
		Scheduler sc = SchedulerExecutor.getScheduler();
		//		deleteTrigger("aaa", "group1");
		addTrigger("triggerC", "group3", "0/5 * * * * ?", C.class);
	}
}
