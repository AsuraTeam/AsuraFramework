/**
 * @FileName: MBeanAnnotationProcessor.java
 * @Package com.asura.framework.monitor.annotation
 * 
 * @author zhangshaobin
 * @created 2013-1-14 上午8:59:05
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.monitor.annotation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.monitor.jmx.server.JmxServer;

/**
 * <p>MBean注解处理器</p>
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
public class MBeanAnnotationProcessor {

	private Logger logger = LoggerFactory.getLogger(MBeanAnnotationProcessor.class);

	private MBeanAnnotationProcessor() {
		loadMBeans(true);
	}

	public static MBeanAnnotationProcessor getInstance() {
		return new MBeanAnnotationProcessor();
	}

	/**
	 * 
	 * 根据注解加载MBean
	 *
	 * @author zhangshaobin
	 * @created 2013-1-22 上午9:14:10
	 *
	 */
	protected void loadMBeans(boolean isServer) {
		File file;
		JarFile jarFile;
		Enumeration<JarEntry> s;
		JarEntry entry;
		Class<?> clazz;
		String className = "";
		MBean mBean;
		String classPath = System.getProperty("java.class.path");
		for (String path : Arrays.asList(classPath.split(System.getProperty("path.separator")))) {
			file = new File(path);
			if (file.isFile()) {
				if ((file.getName().startsWith("com-asura-") || file.getName().startsWith("com.asura.") || file.getName().startsWith("com.asura-") || file
						.getName().startsWith("sms-"))) {
					try {
						jarFile = new JarFile(file);
						s = jarFile.entries();
						while (s.hasMoreElements()) {
							entry = s.nextElement();
							className = entry.getName();
							if (!entry.isDirectory() && className.endsWith(".class")) {
								clazz = Class.forName(className.substring(0, className.length() - 6).replace("/", "."),
										false, Thread.currentThread().getContextClassLoader());
								mBean = clazz.getAnnotation(MBean.class);
								if (mBean != null) {
									if (isServer) {
										boolean flag = JmxServer.getInstance().registMBean(clazz.getName(),
												mBean.name());
										if (!flag) {
											System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ")
													.format(new Date()) + "Registed mbean ERROR: " + clazz.getName());
										}
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
								+ " MBean获取异常 " + file.getName() + " is not a jar file!" + "" + e.getMessage());

						System.exit(1);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
								+ " MBean获取异常 " + "Could not found class " + className + " !" + e.getMessage());
						System.exit(1);
					} catch (BusinessException e) {
						e.printStackTrace();
						System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date())
								+ e.getMessage());
						System.exit(1);
					}
				}
			} else {
				if (file.getPath().contains("classes")) {
					readDir(file.getPath(), "", isServer);
				}
			}
		}
	}

	/**
	 * 
	 * 处理未打包在jar中的class文件
	 *
	 * @author zhangshaobin
	 * @created 2013-1-22 上午9:13:33
	 *
	 * @param path	classPath
	 * @param packageName	包名
	 */
	private void readDir(String path, String packageName, boolean isServer) {
		String className = "";
		Class<?> clazz;
		MBean mBean;
		File[] files = new File(path).listFiles();
		if (null == files)
			return;
		for (File f : Arrays.asList(files)) {
			if (f.isDirectory()) {
				if ("".equals(packageName)) {
					readDir(f.getPath(), f.getName(), isServer);
				} else {
					readDir(f.getPath(), packageName + "." + f.getName(), isServer);
				}
			} else {
				if (f.getName().endsWith(".class")) {
					try {
						className = f.getName();
						clazz = Class.forName(packageName + "."
								+ className.substring(0, f.getName().length() - 6).replace("/", "."), false, Thread
								.currentThread().getContextClassLoader());
						//						System.out.println("	" + clazz.getName());
						mBean = clazz.getAnnotation(MBean.class);
						if (mBean != null) {
							if (isServer) {
								boolean flag = JmxServer.getInstance().registMBean(clazz.getName(), mBean.name());
								if (!flag) {
									System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ")
											.format(new Date()) + "Registed mbean ERROR: " + clazz.getName());
								}
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						logger.error("Could not found class " + className + " !", e.getCause());
					}
				}
			}
		}
	}

}

//@SupportedSourceVersion(SourceVersion.RELEASE_7) 
//@SupportedAnnotationTypes("com.sfbest.monitor.annotation.MBean") 
//class AssignmentProcess extends AbstractProcessor {
//
//	private TypeElement assignmentElement = null;
//
//	public synchronized void init(ProcessingEnvironment processingEnv) {
//		System.out.println("init");
//		super.init(processingEnv);
//		Elements elementUtils = processingEnv.getElementUtils();
//		assignmentElement = elementUtils.getTypeElement("com.sfbest.monitor.annotation.MBean");
//	}
//
//	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//		System.out.println("process");
//		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(assignmentElement);
//		for (Element element : elements) {
//			processAssignment(element);
//		}
//		return true;
//	}
//
//	private void processAssignment(Element element) { 
//		List<? extends AnnotationMirror> annotations = element.getAnnotationMirrors(); 
//		for (AnnotationMirror mirror : annotations) {
//			if (mirror.getAnnotationType().asElement().equals(assignmentElement)) {
//				Map<? extends ExecutableElement, ? extends AnnotationValue> values = mirror.getElementValues();
//				System.out.println(values.size());
//				String assignee = (String) getAnnotationValue(values, "assignee"); //获取注解的值 
//			}
//		} 
//	}
//}
