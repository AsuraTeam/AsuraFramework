/**
 * @FileName: CallbackAdapter.java
 * @Package com.asura.framework.cache.redisThree.callback
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午4:10:40
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache.redisThree.callback;

import java.util.Map;

/**
 * 回调适配器
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
public class CallbackAdapter<T> implements Callback<T> {

	@Override
	public T callback(final T t) throws Exception {
		return null;
	}

	@Override
	public T callback(final Map<String, String> map) throws Exception {
		return null;
	}

}
