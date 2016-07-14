package com.appleframework.boot.core.monitor;

import com.appleframework.boot.utils.SystemPropertiesUtils;

public class MonitorConfig implements MonitorConfigMBean {

	private static String KEY_APPLICATION_NAME = "application.name";
	private static String KEY_DATA_ID          = "deploy.dataId";
	private static String KEY_GROUP            = "deploy.group";
	private static String KEY_ENV              = "deploy.env";
	private static String KEY_SERVICE_PORT     = "service.port";
	private static String KEY_WEB_PORT         = "web.port";
	private static String KEY_JMX_PORT         = "jmx.port";
	private static String KEY_INSTALL_PATH     = "install.path";
	private static String KEY_START_PARAM      = "start.param";
	private static String KEY_MEM_MAX          = "mem.max";
	private static String KEY_MEM_MIN          = "mem.min";

	@Override
	public String getApplicationName() {
		return SystemPropertiesUtils.getString(KEY_APPLICATION_NAME);
	}

	@Override
	public String getDataId() {
		return SystemPropertiesUtils.getString(KEY_DATA_ID);
	}

	@Override
	public String getGroup() {
		return SystemPropertiesUtils.getString(KEY_GROUP);
	}

	@Override
	public String getEnv() {
		return SystemPropertiesUtils.getString(KEY_ENV);
	}

	@Override
	public Integer getServicePort() {
		return SystemPropertiesUtils.getInteger(KEY_SERVICE_PORT);
	}

	@Override
	public Integer getWebPort() {
		return SystemPropertiesUtils.getInteger(KEY_WEB_PORT);
	}

	@Override
	public Integer getJmxPort() {
		return SystemPropertiesUtils.getInteger(KEY_JMX_PORT);
	}

	@Override
	public String getInstallPath() {
		return SystemPropertiesUtils.getString(KEY_INSTALL_PATH);
	}

	@Override
	public String getStartParam() {
		return SystemPropertiesUtils.getString(KEY_START_PARAM);
	}

	@Override
	public String getMemMax() {
		return SystemPropertiesUtils.getString(KEY_MEM_MAX);
	}

	@Override
	public String getMemMin() {
		return SystemPropertiesUtils.getString(KEY_MEM_MIN);
	}
	
	
	
}
