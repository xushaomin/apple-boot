package com.appleframework.boot.config.jmx;

public interface ConfigContainerManagerMBean {

	public String getName();

	public void restart();

	public void start();

	public void stop();

	public boolean isRunning();
}
