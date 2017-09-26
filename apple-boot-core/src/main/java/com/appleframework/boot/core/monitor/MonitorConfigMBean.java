package com.appleframework.boot.core.monitor;

public interface MonitorConfigMBean {

	public String getApplicationName();

	public Integer getServicePort();

	public Integer getWebPort();

	public Integer getJmxPort();

	public String getInstallPath();
	
	public String getStartParam();
	
	public String getMemMax();
	
	public String getMemMin();
		
}
