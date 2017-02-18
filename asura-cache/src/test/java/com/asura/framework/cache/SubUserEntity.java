/**
 * @FileName: SubUserEntity.java
 * @Package com.asura.framework.cache
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午7:57:13
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.cache;

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
public class SubUserEntity extends UserEntity {

	private String wife;

	private boolean isDo;

	/**
	 * @return the wife
	 */
	public String getWife() {
		return wife;
	}

	/**
	 * @param wife the wife to set
	 */
	public void setWife(final String wife) {
		this.wife = wife;
	}

	/**
	 * @return the isDo
	 */
	public boolean isDo() {
		return isDo;
	}

	/**
	 * @param isDo the isDo to set
	 */
	public void setDo(final boolean isDo) {
		this.isDo = isDo;
	}

	@Override
	public String toString() {
		final StringBuilder tostr = new StringBuilder();
		tostr.append(super.toString()).append(",");
		tostr.append("wife:").append(wife).append(",");
		tostr.append("isDo:").append(isDo);
		return tostr.toString();
	}
}
