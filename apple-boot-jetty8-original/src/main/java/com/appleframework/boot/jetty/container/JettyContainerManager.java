package com.appleframework.boot.jetty.container;

import com.appleframework.boot.core.Container;

/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class JettyContainerManager implements JettyContainerManagerMBean {

	Container springContainer;

	@Override
	public String getName() {
		return springContainer.getName();
	}

	@Override
	public void restart() {
		springContainer.restart();
	}

	@Override
	public void start() {
		springContainer.start();
	}

	@Override
	public void stop() {
		springContainer.stop();
	}

	@Override
	public boolean isRunning() {
		return springContainer.isRunning();
	}
	
	public void setSpringContainer(Container springContainer) {
		this.springContainer = springContainer;
	}

}