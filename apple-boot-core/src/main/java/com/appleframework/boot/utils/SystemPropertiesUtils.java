package com.appleframework.boot.utils;

import java.io.IOException;
import java.io.InputStream;
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
	
	/*public PropertiesConfig(File file){
		load(file);
	}*/
	
	static {
		load(ResourceUtils.getAsStream(SYSTEM_PROPERTIES));
	}
	
	public SystemPropertiesUtils(String name) {
				
	}
	
	private static void load(InputStream is){
		prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {			
			logger.error("error happen when loading properties file:", e);
		}
	}
	
	public static String getString(String key){
		return prop.getProperty(key);
	}
	
	public static String getApplicationName(){
		return getString(APPLICATION_NAME_KEY);
	}
	
}
