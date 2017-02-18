/**
 * @FileName: UserEntity.java
 * @Package com.asura.framework.cache
 * 
 * @author zhangshaobin
 * @created 2014年12月9日 下午5:00:44
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
public class UserEntity {

	private int id;

	private String name;

	private String realname;

	private String password;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}

	/**
	 * @param realname the realname to set
	 */
	public void setRealname(final String realname) {
		this.realname = realname;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		final StringBuilder tostr = new StringBuilder();
		tostr.append("id:").append(id).append(",");
		tostr.append("name:").append(name).append(",");
		tostr.append("realname:").append(realname).append(",");
		tostr.append("password:").append(password);
		return tostr.toString();
	}
}
