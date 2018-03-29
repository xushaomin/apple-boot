package com.appleframework.boot.jmx;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.jmx.ContainerManagerMBean;

/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class ContainerManager implements ContainerManagerMBean {

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