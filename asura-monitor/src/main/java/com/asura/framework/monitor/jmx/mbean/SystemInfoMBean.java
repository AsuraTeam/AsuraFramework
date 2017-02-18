/**
 * @FileName: SystemInfoMBean.java
 * @Package com.asura.framework.monitor.jmx.mbean
 * 
 * @author zhangshaobin
 * @created 2013-1-7 下午8:15:21
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.jmx.mbean;

/**
 * <p>系统信息</p>
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
public interface SystemInfoMBean {

	public final static String SYSTEMINFO_MXBEAN_NAME = "com.asura:type=SystemInfo";

	/**
	 * 
	 * 操作系统物理内存总数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:53:29
	 *
	 * @return
	 */
	public long getTotalPhysicalMemorySize();

	/**
	 * 
	 * 操作系统空闲物理内存总数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:53:54
	 *
	 * @return
	 */
	public long getFreePhysicalMemorySize();

	/**
	 * 
	 * JVM最大能识别的内存数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:54:12
	 *
	 * @return
	 */
	public long getMaxJvmMemorySize();

	/**
	 * 
	 * JVM总内存数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:54:47
	 *
	 * @return
	 */
	public long getTotalJvmMemorySize();

	/**
	 * 
	 * JVM空闲内存数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:55:09
	 *
	 * @return
	 */
	public long getFreeJvmMemorySize();

	/**
	 * 
	 * JVM启动时间
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:55:29
	 *
	 * @return
	 */
	public long getJvmStartTime();

	/**
	 * 
	 * JVM版本
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:55:54
	 *
	 * @return
	 */
	public String getJvmVersion();

	/**
	 * 
	 * JVM实现名称
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:56:22
	 *
	 * @return
	 */
	public String getJvmName();

	/**
	 * 
	 * JVM启动后加载的Class总数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:56:31
	 *
	 * @return
	 */
	public long getTotalLoadedClassCount();

	/**
	 * 
	 * JVM当前加载的Class数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:57:06
	 *
	 * @return
	 */
	public long getLoadedClassCount();

	/**
	 * 
	 * JVM初始堆大小
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:57:46
	 *
	 * @return
	 */
	public long getHeapMemoryInit();

	/**
	 * 
	 * JVM堆使用大小
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:58:38
	 *
	 * @return
	 */
	public long getHeapMemoryUsed();

	/**
	 * 
	 * JVM支持的最大堆内存
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:59:01
	 *
	 * @return
	 */
	public long getHeapMemoryMax();

	/**
	 * 
	 * 当前有效线程数，包括守护线程和非守护线程
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午8:59:15
	 *
	 * @return
	 */
	public long getThreadCount();

	/**
	 * 
	 * 自JVM启动或重置后活动线程的峰值
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午9:00:24
	 *
	 * @return
	 */
	public long getPeakThreadCount();

	/**
	 * 
	 * 守护线程数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午9:02:17
	 *
	 * @return
	 */
	public long getDaemonThreadCount();

	/**
	 * 
	 * 自JVM启动后创建的线程总数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-7 下午9:02:41
	 *
	 * @return
	 */
	public long getTotalStartedThreadCount();

}
