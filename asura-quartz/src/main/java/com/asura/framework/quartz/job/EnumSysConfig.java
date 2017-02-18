/**
 * @FileName: EnumSysConfig.java
 * @Package com.asura.framework.quartz.job
 * 
 * @author zhangshaobin
 * @created 2014年12月10日 下午9:26:15
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.quartz.job;

import com.asura.framework.conf.subscribe.AsuraSub;

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
@AsuraSub
public enum EnumSysConfig {
	asura_quartzKP("asura", "quartzKP", "on", "是否空跑定时任务,on空跑    off不空跑 ");

	private String type;
	private String code;
	private String defaultValue;
	private String notes;

	private EnumSysConfig(String type, String code, String defaultValue, String notes) {
		this.code = code;
		this.type = type;
		this.notes = notes;
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
