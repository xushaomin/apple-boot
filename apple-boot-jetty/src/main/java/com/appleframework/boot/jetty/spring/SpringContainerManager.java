package com.appleframework.boot.jetty.spring;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.jmx.SpringContainerManagerMBean;

/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class SpringContainerManager implements SpringContainerManagerMBean {

	Container springContainer = new SpringContainer();

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

}