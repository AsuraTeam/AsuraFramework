/**
 * @FileName: ApplicationNodeListener.java
 * @Package com.asura.framework.monitor.listener
 * 
 * @author zhangshaobin
 * @created 2013-1-9 上午11:55:06
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.listener;

import java.util.List;

import com.asura.framework.monitor.entity.ApplicationNode;

/**
 * <p>被监控应用在ZKServer上注册节点信息</p>
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
public interface ApplicationNodeListener {
	
	/**
	 * 
	 * 节点变化
	 *
	 * @author zhangshaobin
	 * @created 2013-1-9 下午1:30:19
	 *
	 * @param applicationNodes
	 */
	public void nodeChange(List<ApplicationNode> applicationNodes);
	
}
