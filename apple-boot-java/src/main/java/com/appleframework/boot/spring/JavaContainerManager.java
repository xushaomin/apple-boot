package com.appleframework.boot.spring;

import com.appleframework.boot.core.Container;

/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class JavaContainerManager implements JavaContainerManagerMBean {
	
	Container container;

	@Override
	public String getName() {
		return container.getName();
	}

	@Override
	public void restart() {
		container.restart();
	}
	
	@Override
	public void start() {
		container.start();
	}

	@Override
	public void stop() {
		container.stop();
		
	}

	@Override
	public boolean isRunning() {
		return container.isRunning();
	}

	public void setContainer(Container container) {
		this.container = container;
	}
   
}