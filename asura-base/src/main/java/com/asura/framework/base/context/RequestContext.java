/**
 * @FileName: RequestContext.java
 * @Package com.sfbest.framework.context
 * 
 * @author zhangshaobin
 * @created 2012-12-10 下午4:31:03
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.base.context;

import com.alibaba.dubbo.rpc.RpcContext;

/**
 * <p>请求的上下文信息</p>
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
public class RequestContext {
	
	private RequestContext() {}
	
	private static RequestContext rc = new RequestContext();
	
	/**
	 * 
	 * 获得请求上下文对象
	 *
	 * @author zhangshaobin
	 * @created 2012-12-10 下午4:38:49
	 *
	 * @return	请求上下文对象
	 */
	public static RequestContext getContext() {
		return rc;
	}

	/**
	 * 
	 * 获取请求者地址
	 *
	 * @author zhangshaobin
	 * @created 2012-12-10 下午4:39:32
	 *
	 * @return	请求者地址
	 */
	public String getRemoteAddress() {
		return RpcContext.getContext().getRemoteHost();
	}
	
	/**
	 * 
	 * 获取请求者端口
	 *
	 * @author zhangshaobin
	 * @created 2012-12-10 下午4:40:15
	 *
	 * @return	请求者端口
	 */
	public int getRemotePort() {
		return RpcContext.getContext().getRemotePort();
	}
	
	/**
	 * 
	 * 获取本地地址
	 *
	 * @author zhangshaobin
	 * @created 2012-12-10 下午4:50:15
	 *
	 * @return	本地地址
	 */
	public String getLocalAddress() {
		return RpcContext.getContext().getLocalHost();
	}
	
	/**
	 * 
	 * 获取本地端口
	 *
	 * @author zhangshaobin
	 * @created 2012-12-10 下午4:50:44
	 *
	 * @return	本地端口
	 */
	public int getLocalPort() {
		return RpcContext.getContext().getLocalPort();
	}
	
}
