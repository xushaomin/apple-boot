package com.appleframework.boot.jmx;

import com.appleframework.boot.core.Container;

public interface ContainerManagerMBean {

	public String getName();

	public void restart();

	public void start();

	public void stop();

	public boolean isRunning();
	
	public void setContainer(Container container);
	
}
