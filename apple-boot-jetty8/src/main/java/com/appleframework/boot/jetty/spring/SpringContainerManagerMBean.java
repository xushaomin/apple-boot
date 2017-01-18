package com.appleframework.boot.jetty.spring;

public interface SpringContainerManagerMBean {

	public String getName();

	public void restart();

	public void start();

	public void stop();

	public boolean isRunning();
}
