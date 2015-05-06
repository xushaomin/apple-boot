package com.appleframework.boot.core.monitor;

public interface MonitorConfigMBean {

	public String getApplicationName();

	public String getDataId();

	public String getGroup();

	public String getEnv();

	public Integer getServicePort();

	public Integer getWebPort();

	public Integer getJmxPort();

	public String getInstallPath();
	
	public String getProtocolName();
	
}
