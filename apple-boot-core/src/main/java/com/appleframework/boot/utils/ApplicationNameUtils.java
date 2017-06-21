package com.appleframework.boot.utils;

import org.apache.log4j.Logger;

public class ApplicationNameUtils {

	private static Logger logger = Logger.getLogger(ApplicationNameUtils.class);

	public static String APPLICATION_NAME = null;
	
	public static void setToThreadName() {
		try {
			String name = SystemPropertiesUtils.getApplicationName();
			if(null != name) {
				Thread.currentThread().setName(name);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static String getApplicationName() {
		if(null == APPLICATION_NAME) {
			try {
				APPLICATION_NAME = SystemPropertiesUtils.getApplicationName();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return APPLICATION_NAME;
	}

}
