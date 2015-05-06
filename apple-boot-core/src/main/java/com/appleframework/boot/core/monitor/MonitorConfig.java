package com.appleframework.boot.core.monitor;

import com.appleframework.boot.utils.SystemPropertiesUtils;

public class MonitorConfig implements MonitorConfigMBean {

	// private String applicationName;
	// private String dataId;
	// private String group;
	// private String env;
	// private Integer servicePort;
	// private Integer webPort;
	// private Integer jmxPort;
	// private String installPath;
	// private String protocolName;

	private static String KEY_APPLICATION_NAME = "application.name";
	private static String KEY_DATA_ID          = "deploy.dataId";
	private static String KEY_GROUP            = "deploy.group";
	private static String KEY_ENV              = "deploy.env";
	private static String KEY_SERVICE_PORT     = "service.port";
	private static String KEY_WEB_PORT         = "web.port";
	private static String KEY_JMX_PORT         = "jmx.port";
	private static String KEY_INSTALL_PATH     = "install.path";
	private static String KEY_PROTOCOL_NAME    = "protocol.name";
	

	public String getApplicationName() {
		return SystemPropertiesUtils.getString(KEY_APPLICATION_NAME);
	}

	public String getDataId() {
		return SystemPropertiesUtils.getString(KEY_DATA_ID);
	}

	public String getGroup() {
		return SystemPropertiesUtils.getString(KEY_GROUP);
	}

	public String getEnv() {
		return SystemPropertiesUtils.getString(KEY_ENV);
	}

	public Integer getServicePort() {
		return SystemPropertiesUtils.getInteger(KEY_SERVICE_PORT);
	}

	public Integer getWebPort() {
		return SystemPropertiesUtils.getInteger(KEY_WEB_PORT);
	}

	public Integer getJmxPort() {
		return SystemPropertiesUtils.getInteger(KEY_JMX_PORT);
	}

	public String getInstallPath() {
		return SystemPropertiesUtils.getString(KEY_INSTALL_PATH);
	}

	public String getProtocolName() {
		return SystemPropertiesUtils.getString(KEY_PROTOCOL_NAME);
	}
	
	

}
