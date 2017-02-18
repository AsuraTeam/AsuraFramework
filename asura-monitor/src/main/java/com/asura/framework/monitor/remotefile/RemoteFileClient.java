/**
 * @FileName: RemoteFileClient.java
 * @Package com.asura.framework.monitor.remotefile
 * 
 * @author zhangshaobin
 * @created 2013-4-9 上午9:41:42
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.remotefile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * <p>
 * socket客户端
 * </p>
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
public class RemoteFileClient {
	/**
	 * 实例化RemoteFileClient对象
	 */
	private static RemoteFileClient instance = new RemoteFileClient();

	/**
	 * 私有构造器，防止外部实例化
	 */
	private RemoteFileClient() {
	}

	/**
	 * 
	 * 外部调用获取RemoteFileClient实例
	 * 
	 * @author zhangshaobin
	 * @created 2013-4-9 上午9:44:55
	 * 
	 * @return
	 */
	public static RemoteFileClient getInstance() {
		return instance;
	}

	/**
	 * 
	 * 根据文件夹路径后去其下面文件列表名以及文件大小
	 * 
	 * @author zhangshaobin
	 * @created 2013-4-9 上午9:52:00
	 * 
	 * @param ip
	 *            地址
	 * @param filePath
	 *            文件夹路径
	 * @return 格式：文件名|大小，文件名|大小
	 */
	@SuppressWarnings("resource")
	public String getFileNames(String ip, String filePath) {
		String fileListNames = "";
		BufferedReader socketReader = null;
		PrintWriter socketWriter = null;
		try {
			Socket client = new Socket(ip, 9050);
			client.setSoTimeout(5000);
			socketReader = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
			socketWriter = new PrintWriter(client.getOutputStream());
		} catch (UnknownHostException e) {
			System.out
					.println("getFileNamesByFolder Error creating socket connection: unknown host at " + ip + ":9050");
		} catch (IOException e) {
			System.out.println("getFileNamesByFolder Error creating up socket connection: " + e);
		}
		try {
			if (socketReader != null && socketWriter != null) {
				socketWriter.println(filePath);
				socketWriter.flush();
				fileListNames = socketReader.readLine();
			}
		} catch (IOException e) {
			System.out.println("getFileNamesByFolder Error reading from file: " + filePath);
		}

		try {
			if (socketReader != null && socketWriter != null) {
				socketWriter.close();
				socketReader.close();
			}
		} catch (IOException e) {
			System.out.println("getFileNamesByFolder Error closing socket connection: " + e);
		}
		return fileListNames;
	}

	/**
	 * 
	 * 获取文件内容
	 * 
	 * @author zhangshaobin
	 * @created 2013-4-9 上午10:49:21
	 * 
	 * @param ip
	 * @param filePath
	 * @return
	 */
	@SuppressWarnings("resource")
	public void getFileContent(String ip, String filePath, PrintWriter out) {
		BufferedReader socketReader = null;
		PrintWriter socketWriter = null;
		try {
			Socket client = new Socket(ip, 9050);
			client.setSoTimeout(10000);
			socketReader = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
			socketWriter = new PrintWriter(client.getOutputStream());
			socketWriter.println(filePath);
			socketWriter.flush();
			String line = null;
			while ((line = socketReader.readLine()) != null) {
				out.write(line + "\r\n");
			}
		} catch (UnknownHostException e) {
			System.out.println("getFileContent Error creating socket connection: unknown host at " + ip + ":9050");
		} catch (IOException e) {
			System.out.println("getFileContent Error creating up socket connection: " + e);
		} finally {
			if (socketWriter != null) {
				socketWriter.close();
			}
			if (socketReader != null) {
				try {
					socketReader.close();
				} catch (IOException e) {
					System.out.println("getFileContent Error closing socket connection: " + e);
				}
			}
		}
	}

}
