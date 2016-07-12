package com.appleframework.boot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author xusm(2012-11-22)
 *
 */
public class SystemPropertiesUtils {
	
	private static Logger logger = Logger.getLogger(SystemPropertiesUtils.class);
	
	private static final String SYSTEM_PROPERTIES = "system.properties";
	
	public static final String APPLICATION_NAME_KEY = "application.name";
	
	private static Properties prop = null;
	
	static {
		load(ResourceUtils.getAsStream(SYSTEM_PROPERTIES));
	}
		
	private static void load(InputStream is){
		prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {			
			logger.error("error happen when loading properties file:", e);
		}
	}
	
	
	public static String getApplicationName(){
		return getString(APPLICATION_NAME_KEY);
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
			logger.warn("配置项为" + key + "的配置未在" + SYSTEM_PROPERTIES +"中添加或设置的内容为空");
			return null;
		}
	}
	
	public static String getValue(String key, String defaultValue) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在" + SYSTEM_PROPERTIES +"中添加或设置的内容为空");
			return defaultValue;
		}
	}
	
	public static String getString(String key) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在" + SYSTEM_PROPERTIES +"中添加或设置的内容为空");
			return null;
		}
	}
	
	public static String getString(String key, String defaultString) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在" + SYSTEM_PROPERTIES +"中添加或设置的内容为空");
			return defaultString;
		}
	}
	
	public static Long getLong(String key) {
		Object object = getProperty(key);
		if(null != object)
			return Long.parseLong(object.toString());
		else {
			logger.warn("配置项为" + key + "的配置未在" + SYSTEM_PROPERTIES +"中添加或设置的内容为空");
			return null;
		}
	}
	
	public static Long getLong(String key, long defaultLong) {
		Object object = getProperty(key);
		if(null != object)
			return Long.parseLong(object.toString());
		else {
			logger.warn("配置项为" + key + "的配置未在" + SYSTEM_PROPERTIES +"中添加或设置的内容为空");
			return defaultLong;
		}
	}
	
	public static Integer getInteger(String key) {
		Object object = getProperty(key);
		if(null != object) {
			return Integer.parseInt(object.toString());
		}
		else {
			logger.warn("配置项为" + key + "的配置未在" + SYSTEM_PROPERTIES +"中添加或设置的内容为空");
			return null;
		}
	}
	
	public static Integer getInteger(String key, int defaultInt) {
		Object object = getProperty(key);
		if(null != object) {
			return Integer.parseInt(object.toString());
		}
		else {
			logger.warn("配置项为" + key + "的配置未在" + SYSTEM_PROPERTIES +"中添加或设置的内容为空");
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
