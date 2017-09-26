package com.appleframework.boot.utils;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.appleframework.config.core.EnvConfigurer;

/**
 * @author xusm(2012-11-22)
 *
 */
public class SystemPropertiesUtils {
	
	private static Logger logger = Logger.getLogger(SystemPropertiesUtils.class);
	
	private static final String SYSTEM_PROPERTIES = "system.properties";
	private static final String SPRING_PROPERTIES = "application.properties";
	
	private static final String APPLE_APPLICATION_NAME_KEY  = "application.name";
	private static final String SPRING_APPLICATION_NAME_KEY = "spring.application.name";
	
	private static final String CLASSPATH_URL_PREFIX = "classpath:";
	
	private static Properties prop = new Properties();
	
	static {
		load(SYSTEM_PROPERTIES);
		load(SPRING_PROPERTIES);
		load(CLASSPATH_URL_PREFIX + SYSTEM_PROPERTIES);
		load(CLASSPATH_URL_PREFIX + SPRING_PROPERTIES);
	}
		
	private static void load(String fileName) {
		Properties props = new Properties();
		try {
			props.load(ResourceUtils.getAsStream(fileName));
			convertProperties(props);
		} catch (Exception e) {
			logger.error("the properties file is not exist : " + fileName);
		}
	}
	
	public static void convertProperties(Properties defaultProps) {
		Enumeration<?> propertyNames = defaultProps.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String propertyName = (String) propertyNames.nextElement();
			String propertyValue = defaultProps.getProperty(propertyName);
			if (ObjectUtils.isNotEmpty(propertyName)) {
				prop.setProperty(propertyName, propertyValue);
			}
		}
	}
	
	public static void setProperty(String key, String value) {
		try {
			prop.setProperty(key, value);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void put(Object key, Object value) {
		try {
			prop.put(key, value);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public static String getApplicationName() {
		String name = getString(APPLE_APPLICATION_NAME_KEY);
		if(null == name) {
			name = getString(SPRING_APPLICATION_NAME_KEY);
		}
		return name;
	}
	
	public static String getEnv() {
		String env = getString(EnvConfigurer.KEY_APPLE_DEPLOY_ENV);
		if(null == env) {
			env = getString(EnvConfigurer.KEY_SPRING_DEPLOY_ENV);
		}
		return env;
	}

	public static Properties getProp() {
		return prop;
	}
	
	public static Object getProperty(String key) {
		if(null == prop)
			return null;
		else
			return prop.get(key);
	}
	
	public static String getValue(String key) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在properties文件中添加或设置的内容为空");
			return null;
		}
	}
	
	public static String getValue(String key, String defaultValue) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在properties文件中添加或设置的内容为空");
			return defaultValue;
		}
	}
	
	public static String getString(String key) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在properties文件中添加或设置的内容为空");
			return null;
		}
	}
	
	public static String getString(String key, String defaultString) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在properties文件中添加或设置的内容为空");
			return defaultString;
		}
	}
	
	public static Long getLong(String key) {
		Object object = getProperty(key);
		if(null != object)
			return Long.parseLong(object.toString());
		else {
			logger.warn("配置项为" + key + "的配置未在properties文件中添加或设置的内容为空");
			return null;
		}
	}
	
	public static Long getLong(String key, long defaultLong) {
		Object object = getProperty(key);
		if(null != object)
			return Long.parseLong(object.toString());
		else {
			logger.warn("配置项为" + key + "的配置未在properties文件中添加或设置的内容为空");
			return defaultLong;
		}
	}
	
	public static Integer getInteger(String key) {
		Object object = getProperty(key);
		if(null != object) {
			return Integer.parseInt(object.toString());
		}
		else {
			logger.warn("配置项为" + key + "的配置未在properties文件中添加或设置的内容为空");
			return null;
		}
	}
	
	public static Integer getInteger(String key, int defaultInt) {
		Object object = getProperty(key);
		if(null != object) {
			return Integer.parseInt(object.toString());
		}
		else {
			logger.warn("配置项为" + key + "的配置未在properties文件中添加或设置的内容为空");
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
	
}
