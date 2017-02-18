/**
 * @FileName: RemoteFileServer.java
 * @Package com.asura.framework.monitor.remotefile
 * 
 * @author zhangshaobin
 * @created 2013-4-7 下午3:06:51
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.remotefile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;

/**
 * <p>
 * socekt远程调用文件
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
public final class RemoteFileServer {

	/**
	 * 日志对象
	 */
	Logger logger = LoggerFactory.getLogger(RemoteFileServer.class);

	/**
	 * 实例化RemoteFileServer
	 */
	private static RemoteFileServer instance = new RemoteFileServer();

	/**
	 * 处理socket客户端请求的线程池
	 */
	private final ThreadPoolExecutor executeScoketthreadPoolExecutor = new ThreadPoolExecutor(30, 50, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	/**
	 * 单线程线程池
	 */
	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	/**
	 * 服务端socket
	 */
	private ServerSocket serverSocket;

	/**
	 * 端口号
	 */
	private final int readFilePort = 9050;

	/**
	 * 私有构造器
	 */
	private RemoteFileServer() {
		start();
	}

	/**
	 * 
	 * 获取实例
	 * 
	 * @author zhangshaobin
	 * @created 2013-4-7 下午3:10:17
	 * 
	 * @return
	 */
	public static RemoteFileServer getInstance() {
		return instance;
	}

	/**
	 * 
	 * 开启读取文件socketServer 包含了读取文件内容和文件夹下文件列表 读取文件列表写入方式为：文件名|大小，文件名|大小
	 * 
	 * @author zhangshaobin
	 * @created 2013-4-8 上午9:35:37
	 * 
	 */
	private void start() {
		logger.info("开启socketServer");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(readFilePort);
				} catch (IOException e) {
					logger.error("创建ServerSocket时出现异常，IO。", e.getCause());
					throw new BusinessException("创建ServerSocket时出现异常，IO。", e.getCause());
				}
				Socket incomingConnection;
				while (true) {
					try {
						incomingConnection = serverSocket.accept();
						executeScoketthreadPoolExecutor.execute(new SingleThread(incomingConnection));
					} catch (IOException e) {
						logger.error("serverSocket获取客户端连接出现异常，IO。", e.getCause());
						throw new BusinessException("serverSocket获取客户端连接出现异常，IO。", e.getCause());
					}
				}
			}
		});
	}

	/**
	 * 
	 * <p>
	 * 处理客户端请求的线程
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
	class SingleThread implements Runnable {

		Socket socket;

		public SingleThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String pathName = reader.readLine();
				File file = new File(pathName);
				PrintWriter streamWriter;
				if (file.exists()) {
					if (file.isFile()) {
						FileReader fileReader = new FileReader(file);
						BufferedReader bufferedFileReader = new BufferedReader(fileReader);
						streamWriter = new PrintWriter(socket.getOutputStream());
						String line = null;
						while ((line = bufferedFileReader.readLine()) != null) {
							streamWriter.println(line);
						}
						bufferedFileReader.close();
						fileReader.close();
					} else {
						StringBuffer fileFullNames = new StringBuffer();
						long fileLength = 0L;
						File subFile;
						for (String fileFullName : file.list()) {
							subFile = new File(pathName + "/" + fileFullName);
							fileLength = subFile.length();
							fileFullNames.append(fileFullName + "|" + fileLength + ",");
						}
						streamWriter = new PrintWriter(socket.getOutputStream());
						streamWriter.println(fileFullNames.toString());
					}
					streamWriter.close();
				}
				reader.close();
				socket.close();
			} catch (FileNotFoundException e) {
				logger.error("FileNotFoundException----SingleThread", e.getCause());
				throw new BusinessException("FileNotFoundException----SingleThread", e.getCause());
			} catch (IOException e) {
				logger.error("IOException----SingleThread，IO。", e.getCause());
				throw new BusinessException("IOException----SingleThread，IO。", e.getCause());
			}

		}

	}

}
