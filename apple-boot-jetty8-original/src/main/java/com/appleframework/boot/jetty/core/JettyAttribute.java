package com.appleframework.boot.jetty.core;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.Attributes;

import com.appleframework.boot.utils.ResourceUtils;

/**
 * @author xusm(2012-11-22)
 *
 */
public class JettyAttribute implements Attributes {

	private static Logger logger = Logger.getLogger(JettyAttribute.class);

	private static final String PROPERTIES_JETTY       = "jetty.properties";
	private static final String PROPERTIES_SYSTEM      = "system.properties";
	private static final String PROPERTIES_APPLICATION = "application.properties";

	public static final String WEB_CONTEXT = "web.context";
	public static final String WEB_PORT    = "web.port";
	
	public static final String MIN_THREADS  = "web.min.threads";
	public static final String MAX_THREADS  = "web.max.threads";
	public static final String MAX_QUEUED   = "web.max.queued";
	
	private static Properties prop = new Properties();

	static {
		try {
			load(ResourceUtils.getAsStream(PROPERTIES_JETTY));
		} catch (Exception e) {
			logger.error("error happen when loading jetty.properties file");
		}
		try {
			load(ResourceUtils.getAsStream(PROPERTIES_SYSTEM));
		} catch (Exception e) {
			logger.error("error happen when loading system.properties file");
		}
		try {
			load(ResourceUtils.getAsStream(PROPERTIES_APPLICATION));
		} catch (Exception e) {
			logger.error("error happen when loading application.properties file");
		}
	}

	private static void load(InputStream is) {
		Properties props = new Properties();
		try {
			props.load(is);
			convertProperties(props);
		} catch (IOException e) {
			logger.error("error happen when loading properties file");
		}
	}
	
	public static void convertProperties(Properties defaultProps) {
		Enumeration<?> propertyNames = defaultProps.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String propertyName = (String) propertyNames.nextElement();
			String propertyValue = defaultProps.getProperty(propertyName);
			if (null != propertyName) {
				prop.setProperty(propertyName, propertyValue);
			}
		}
	}

	public static Properties getProp() {
		return prop;
	}

	@SuppressWarnings("rawtypes")
	public static Iterator getIterator() {
		return prop.entrySet().iterator();
	}

	@Override
	public void removeAttribute(String name) {
		prop.remove(name);
	}

	@Override
	public void setAttribute(String name, Object attribute) {
		prop.setProperty(name, attribute.toString());
	}

	@Override
	public Object getAttribute(String name) {
		if (null == prop)
			return null;
		else
			return prop.get(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration<String> getAttributeNames() {
		return (Enumeration<String>) prop.propertyNames();
	}

	@Override
	public void clearAttributes() {
		prop.clear();
	}
	
	public static String getValue(String key, String defaultValue) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在配置中心或项目中添加或设置的内容为空");
			return defaultValue;
		}
	}
	public static Integer getInteger(String key, int defaultInt) {
		Object object = getProperty(key);
		if(null != object) {
			return Integer.parseInt(object.toString());
		}
		else {
			logger.warn("配置项为" + key + "的配置未在配置中心或项目中添加或设置的内容为空");
			return defaultInt;
		}
	}
	
	public static String getString(String key, Object[] array) {
		String message = getValue(key);
		if(null != message) {
			return MessageFormat.format(message, array);  
		}
		else {
			return null;
		}
	}
	
	public static String getValue(String key, Object... array) {
		String message = getValue(key);
		if(null != message) {
			return MessageFormat.format(message, array);  
		}
		else {
			return null;
		}
	}
	
	public static Object getProperty(String key) {
		return prop.get(key);
	}

}
