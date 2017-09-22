package com.appleframework.boot.jetty.container;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.jmx.ContainerManagerMBean;

/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class JettyContainerManager implements ContainerManagerMBean {

	Container jettyContainer;

	@Override
	public String getName() {
		return jettyContainer.getName();
	}

	@Override
	public void restart() {
		jettyContainer.restart();
	}

	@Override
	public void start() {
		jettyContainer.start();
	}

	@Override
	public void stop() {
		jettyContainer.stop();
	}

	@Override
	public boolean isRunning() {
		return jettyContainer.isRunning();
	}
	
	public void setContainer(Container cntainer) {
		this.jettyContainer = cntainer;
	}

}