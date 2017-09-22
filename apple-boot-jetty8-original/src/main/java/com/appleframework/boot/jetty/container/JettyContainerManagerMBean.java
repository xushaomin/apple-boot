package com.appleframework.boot.jetty.container;

public interface JettyContainerManagerMBean {

	public String getName();

	public void restart();

	public void start();

	public void stop();

	public boolean isRunning();
}
