package com.appleframework.boot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.config.core.AppConfigurer;
import com.appleframework.config.core.EnvConfigurer;

public class ApplicationUtils {

	private static Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);
		
	public static String getApplicationName() {
		String name = AppConfigurer.getName();
		if(null == name) {
			try {
				name = SystemPropertiesUtils.getApplicationName();
				if(null != name) {
					AppConfigurer.setName(name);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return name;
	}
	
	public static String getEnv() {
		String env = EnvConfigurer.getEnv();
		if(null == env) {
			try {
				env = SystemPropertiesUtils.getEnv();
				if(null != env) {
					EnvConfigurer.setEnv(env);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return env;
	}

}
