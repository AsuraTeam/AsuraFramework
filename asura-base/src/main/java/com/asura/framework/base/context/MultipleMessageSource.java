/**
 * @FileName: MultipleMessageSource.java
 * @Package: com.asura.framework.base.context
 * @author sence
 * @created 10/30/2015 7:20 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.base.context;

import com.asura.framework.base.exception.BusinessException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * <p>
 *      可以匹配文件路径的资源加载
 * </p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class MultipleMessageSource extends ReloadableResourceBundleMessageSource {


    private static final String PROPERTIES_SUFFIX = ".properties";


    private ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @Override
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        Properties properties = new Properties();
        long lastModified = -1;
        try {
            Resource[] resources = resolver.getResources(filename + "*" + PROPERTIES_SUFFIX);
            for (Resource resource : resources) {
                String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
                PropertiesHolder holder = super.refreshProperties(sourcePath, propHolder);
                properties.putAll(holder.getProperties());
                if (lastModified < resource.lastModified())
                    lastModified = resource.lastModified();
            }
        } catch (IOException ignored) {
            throw new BusinessException("load message source error:" + ignored);
        }
        return new PropertiesHolder(properties, lastModified);
    }
}
