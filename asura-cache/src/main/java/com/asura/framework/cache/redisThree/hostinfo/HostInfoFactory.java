/**
 * @FileName: HostInfoFactory.java
 * @Package com.asura.framework.cache.redisThree.hostinfo
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:18:43
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree.hostinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author YRJ
 * @since 1.0
 * @version 1.0
 */
public final class HostInfoFactory {

	/** 主分割符 */
	private static final String SPLITER = ",";

	/** 子分割符 */
	private static final String SUB_SPLITER = ":";

	public static final HostInfo[] split(final String host_port_timeout) {
		final String[] host_port_timeouts = host_port_timeout.split(SPLITER);

		final List<HostInfo> hostInfos = new ArrayList<HostInfo>();
		for (final String hpts : host_port_timeouts) {
			final String[] hpt = hpts.split(SUB_SPLITER);
			final List<String> hosts = Arrays.asList(hpt);

			final HostInfo hostInfo = HostInfo.createHostInfo(hosts);
			hostInfos.add(hostInfo);
		}
		return hostInfos.toArray(new HostInfo[0]);
	}

	private HostInfoFactory() {
		throw new AssertionError("Uninstantiable class");
	}
}
