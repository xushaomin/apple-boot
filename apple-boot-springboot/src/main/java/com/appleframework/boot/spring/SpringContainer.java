package com.appleframework.boot.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.utils.ApplicationUtils;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author cruise.xu
 */
public class SpringContainer implements Container {    
    
	private static Logger logger = LoggerFactory.getLogger(SpringContainer.class);

	private static long startTime = System.currentTimeMillis();
    
	public void start() {       
        startTime = System.currentTimeMillis();
        String javaContainer = System.getProperty("java-container");
		if (null != javaContainer) {
			try {
				Class<?> clazz = Class.forName(javaContainer);
		        SpringApplication.run(clazz, new String[0]);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
    }

    public void stop() {
        
    }

	@Override
	public void restart() {}

	@Override
	public boolean isRunning() {
		
    		return true;
    	
	}
	
	@Override
	public String getName() {
    	return ApplicationUtils.getApplicationName();
	}
    
	@Override
	public String getType() {
		return "SpringContainer";
	}

	@Override
	public long getStartTime() {
		return startTime;
	}
}