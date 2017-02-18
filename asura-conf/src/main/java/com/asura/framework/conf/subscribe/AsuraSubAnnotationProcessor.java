/**
 * @FileName: AsuraSubAnnotationProcessor.java
 * @Package com.asura.framework.conf.subscribe
 * @author zhangshaobin
 * @created 2014年12月8日 下午4:32:20
 *
 * Copyright 2011-2015 asura
 */
package com.asura.framework.conf.subscribe;

import com.asura.framework.base.util.Check;
import com.asura.framework.conf.common.CommonConstant;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.netflix.config.DynamicPropertyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>asurasub注解处理器</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangshaobin
 * @version 1.0
 * @since 1.0
 */
public class AsuraSubAnnotationProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsuraSubAnnotationProcessor.class);

    // 要扫描的class文件的包名
    private List<String> classPackageList = new ArrayList<>();

    // 默认要扫描的jar文件前缀
    private String[] classPackagePrefixArr;

    private AsuraSubAnnotationProcessor() {
        classPackageList.add("com.asura");
        String classPackagePrefix = DynamicPropertyFactory.getInstance().getStringProperty(CommonConstant.AUTO_SCAN_CLASS_PACKAGE_PREFIX_KEY, null).get();
        if (!Check.NuNStr(classPackagePrefix)) {
            classPackagePrefixArr = classPackagePrefix.split(",");
            for (String classPackage : classPackagePrefixArr) {
                if (!classPackageList.contains(classPackage.trim())){
                    classPackageList.add(classPackage.trim());
                }
            }
        }
        loadMBeans();
    }

    public static AsuraSubAnnotationProcessor getInstance() {
        return new AsuraSubAnnotationProcessor();
    }

    /**
     * 根据注解加载MBean
     */
    protected void loadMBeans() {
        Class<?> clazz;
        AsuraSub asuraSub;
        try {
            ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            for (String packageName : classPackageList) {
                ImmutableSet<ClassPath.ClassInfo> asuraClassesRecursive = classPath.getTopLevelClassesRecursive(packageName);
                for (ClassPath.ClassInfo classInfo : asuraClassesRecursive) {
                    clazz = Class.forName(classInfo.getName(), false, Thread.currentThread().getContextClassLoader());
                    asuraSub = clazz.getAnnotation(AsuraSub.class);
                    if (asuraSub != null) {
                        toRegistConfig(clazz);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("{} 注册系统参数异常,error:{}", new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date()), e);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            LOGGER.error("{} 注册系统参数异常,error:{}", new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date()), e);
            System.exit(1);
        }
    }

    /**
     * 注册系统常量
     *
     * @param clazz
     */
    private void toRegistConfig(final Class<?> clazz) {
        try {
            Object[] enumObjs = clazz.getEnumConstants();
            if (!Check.NuNObject(enumObjs)) {
                for (Object o : enumObjs) {
                    String appNameObj = null;
                    String typeObj = null;
                    String codeObj = null;
                    Object defaultValue = null;
                    Method[] declaredMethods = o.getClass().getDeclaredMethods();
                    for (Method declaredMethod : declaredMethods) {
                        if (declaredMethod.getName().equals("getAppName")) {
                            appNameObj = (String) declaredMethod.invoke(o);
                        } else if (declaredMethod.getName().equals("getType")) {
                            typeObj = (String) declaredMethod.invoke(o);
                        } else if (declaredMethod.getName().equals("getCode")) {
                            codeObj = (String) declaredMethod.invoke(o);
                        } else if (declaredMethod.getName().equals("getDefaultValue")) {
                            defaultValue = declaredMethod.invoke(o);
                        }
                    }

                    if (typeObj != null && codeObj != null) {
                        ConfigSubscriber.getInstance().registConfig(appNameObj, typeObj, codeObj, defaultValue);
                        LOGGER.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date()) + "Registed Config : appName=" + (appNameObj == null ? "" : appNameObj.toString()) + " type=" + typeObj.toString() + " code=" + codeObj.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("注册系统参数异常", e);
        }
    }

}
