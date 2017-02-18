/**
 * @FileName: C.java
 * @Package com.asura.test.jobs
 * 
 * @author zhangshaobin
 * @created 2014年12月10日 下午6:28:05
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.test.jobs;

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
public class C extends AsuraJob {

	/* (non-Javadoc)
	 * @see com.asura.test.jobs.AsuraJob#run(boolean)
	 */
	@Override
	public void run(boolean flag) {
		System.out.println("C........." + flag);

	}

}
