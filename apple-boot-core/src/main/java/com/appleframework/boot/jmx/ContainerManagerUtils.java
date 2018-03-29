package com.appleframework.boot.jmx;

import com.appleframework.boot.core.Container;

/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class ContainerManagerUtils {
	
	public static ContainerManager instance(Container container) {
		ContainerManager manager = new ContainerManager();
		manager.setContainer(container);
		return manager;
	}

}