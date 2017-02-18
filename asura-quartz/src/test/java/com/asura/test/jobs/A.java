/**
 * @FileName: A.java
 * @Package com.asura.test18
 * 
 * @author zhangshaobin
 * @created 2014年12月2日 下午11:34:40
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
public class A extends AsuraJob {

	/* (non-Javadoc)
	 * @see com.asura.test.jobs.AsuraJob#run()
	 */
	@Override
	public void run(boolean flag) {
		System.out.println("A........." + flag);

	}

}
