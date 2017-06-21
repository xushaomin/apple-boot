package com.appleframework.boot.utils;

import org.apache.log4j.Logger;

import com.appleframework.config.core.AppConfigurer;

public class ApplicationUtils {

	private static Logger logger = Logger.getLogger(ApplicationUtils.class);
		
	public static String getApplicationName() {
		String name = null;
		if(null == AppConfigurer.getName()) {
			try {
				name = SystemPropertiesUtils.getApplicationName();
				if(null != name)
					AppConfigurer.setName(name);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return name;
	}

}
