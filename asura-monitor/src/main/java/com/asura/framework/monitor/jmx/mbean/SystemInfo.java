/**
 * @FileName: SystemInfo.java
 * @Package com.asura.framework.monitor.jmx.mbean
 * 
 * @author zhangshaobin
 * @created 2013-1-7 下午8:15:44
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.jmx.mbean;

import java.lang.management.ManagementFactory;

import com.asura.framework.monitor.annotation.MBean;
import com.sun.management.OperatingSystemMXBean;

/**
 * <p>系统信息实现类</p>
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
@SuppressWarnings("restriction")
@MBean(name = "com.asura:type=SystemInfo")
public class SystemInfo implements SystemInfoMBean {

	/**
	 * 
	 * 获取操作系统MBean
	 *
	 * @author zhangshaobin
	 * @created 2013-1-8 上午8:08:03
	 *
	 * @return
	 */
	private OperatingSystemMXBean getOperatingSystemMXBean() {
		return (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getTotalPhysicalMemorySize()
	 */
	@Override
	public long getTotalPhysicalMemorySize() {
		return getOperatingSystemMXBean().getTotalPhysicalMemorySize();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getFreePhysicalMemorySize()
	 */
	@Override
	public long getFreePhysicalMemorySize() {
		return getOperatingSystemMXBean().getFreePhysicalMemorySize();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getMaxJvmMemorySize()
	 */
	@Override
	public long getMaxJvmMemorySize() {
		return Runtime.getRuntime().maxMemory();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getTotalJvmMemorySize()
	 */
	@Override
	public long getTotalJvmMemorySize() {
		return Runtime.getRuntime().totalMemory();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getFreeJvmMemorySize()
	 */
	@Override
	public long getFreeJvmMemorySize() {
		return Runtime.getRuntime().freeMemory();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getJvmStartTime()
	 */
	@Override
	public long getJvmStartTime() {
		return ManagementFactory.getRuntimeMXBean().getStartTime();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getJvmVersion()
	 */
	@Override
	public String getJvmVersion() {
		return ManagementFactory.getRuntimeMXBean().getVmVersion();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getJvmName()
	 */
	@Override
	public String getJvmName() {
		return ManagementFactory.getRuntimeMXBean().getVmName();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getTotalLoadedClassCount()
	 */
	@Override
	public long getTotalLoadedClassCount() {
		return ManagementFactory.getClassLoadingMXBean().getTotalLoadedClassCount();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getLoadedClassCount()
	 */
	@Override
	public long getLoadedClassCount() {
		return ManagementFactory.getClassLoadingMXBean().getLoadedClassCount();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getHeapMemoryInit()
	 */
	@Override
	public long getHeapMemoryInit() {
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getHeapMemoryUsed()
	 */
	@Override
	public long getHeapMemoryUsed() {
		//		return ManagementFactory.getCompilationMXBean().;
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getHeapMemoryMax()
	 */
	@Override
	public long getHeapMemoryMax() {
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getThreadCount()
	 */
	@Override
	public long getThreadCount() {
		return ManagementFactory.getThreadMXBean().getThreadCount();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getPeakThreadCount()
	 */
	@Override
	public long getPeakThreadCount() {
		return ManagementFactory.getThreadMXBean().getPeakThreadCount();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getDaemonThreadCount()
	 */
	@Override
	public long getDaemonThreadCount() {
		return ManagementFactory.getThreadMXBean().getDaemonThreadCount();
	}

	/* (non-Javadoc)
	 * @see com.sfbest.monitor.mbean.SystemInfoMBean#getTotalStartedThreadCount()
	 */
	@Override
	public long getTotalStartedThreadCount() {
		return ManagementFactory.getThreadMXBean().getTotalStartedThreadCount();
	}

	//	public long getTotalStartedThreadCount() {
	//		return ManagementFactory.getGarbageCollectorMXBeans().;
	//		return ManagementFactory.getMemoryManagerMXBeans();
	//		return ManagementFactory.getMemoryPoolMXBeans().、;
	//	}

}
