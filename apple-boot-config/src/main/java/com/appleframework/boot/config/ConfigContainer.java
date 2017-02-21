package com.appleframework.boot.config;

import org.apache.log4j.Logger;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.utils.SystemPropertiesUtils;
import com.appleframework.config.core.factory.ConfigurerFactory;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class ConfigContainer implements Container {
	
	private static Logger logger = Logger.getLogger(ConfigContainer.class);
	
	private static final String SYSTEM_PROPERTIES_FILE = "system.properties";

	private static long startTime = System.currentTimeMillis();
    
	private ConfigurerFactory factory;
	
	public void start() {
		if(null != factory) {
			factory.setLoadRemote(true);
			factory.init();
		}
	}

    public void stop() {
    }
    
    public void restart() {
    }
    
    public boolean isRunning() {
    	return true;
    }
    
	public ConfigContainer(String configContainer) {
		try {
			Class<?> clazz = Class.forName(configContainer);
			factory = (ConfigurerFactory) clazz.newInstance();
			factory.setSystemPropertyFile(SYSTEM_PROPERTIES_FILE);
		} catch (Exception e) {
			logger.error("The container instance is error : " + e.getMessage());
		}
	}

	@Override
	public String getName() {
    	return SystemPropertiesUtils.getApplicationName();
	}
    
	@Override
	public String getType() {
		return "ConfigContainer";
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	public void setFactory(ConfigurerFactory factory) {
		this.factory = factory;
	}

}