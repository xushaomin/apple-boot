package com.appleframework.boot.core.monitor;

import java.text.MessageFormat;

import com.appleframework.boot.utils.SystemPropertiesUtils;

public class Constants {

	public static String DEF_MONITOR_DOMAIN = "monitor.appleframework.com";
	
	public static String KEY_MONITOR_DOMAIN = "monitor.domain";
	
	public static String KEY_MONITOR_JVM    = "monitor.jvm";
	
	public static String APPLICATION_URL    = "http://{0}/collect/application";
	
	public static String MONITOR_URL        = "http://{0}/collect/monitor";
	
	public static String getMonitorUrl() {
		return MessageFormat.format(Constants.MONITOR_URL, getMonitorDomain());  
	}
	
	public static String getApplicationUrl() {
		return MessageFormat.format(Constants.APPLICATION_URL, getMonitorDomain());  
	}
	
	public static String getMonitorDomain() {
		String domain = System.getProperty(Constants.KEY_MONITOR_DOMAIN);
		if (null == domain) {
			domain = SystemPropertiesUtils.getString(Constants.KEY_MONITOR_DOMAIN);
			if (null == domain)
				domain = Constants.DEF_MONITOR_DOMAIN;
		}
		return domain;
	}
	
	public static boolean isMonitorJvm() {
		String mJvm = System.getProperty(Constants.KEY_MONITOR_JVM);
		if (null == mJvm) {
			mJvm = SystemPropertiesUtils.getString(Constants.KEY_MONITOR_JVM);
		}
		if(null != mJvm) {
			return Boolean.parseBoolean(mJvm);
		}
		return false;
	}

}
