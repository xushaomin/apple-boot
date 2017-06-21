package com.appleframework.boot.utils;

import org.apache.log4j.Logger;

public class ApplicationNameUtils {

	private static Logger logger = Logger.getLogger(ApplicationNameUtils.class);

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

}
