package com.appleframework.boot.core.monitor;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.core.ContainerFactory;

/**
 * LoggingContainer
 * 
 * @author cruise.xu
 */
public class MonitorContainerFactory extends ContainerFactory {
		
	public static Container getContainer() {
		return create(MonitorContainer.class);
	}
	
}