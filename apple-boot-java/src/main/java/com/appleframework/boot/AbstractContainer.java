package com.appleframework.boot;

import org.apache.log4j.Logger;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.utils.SystemPropertiesUtils;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author cruise.xu
 */
public abstract class AbstractContainer implements Container, Runnable {

	private static Logger logger = Logger.getLogger(AbstractContainer.class);
        
	private static long startTime = System.currentTimeMillis();

	public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        try {
            
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

	@Override
	public void restart() {
		try {
			
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
	}

	@Override
	public boolean isRunning() {
    	return true;
	}
	
	@Override
	public String getName() {
    	return SystemPropertiesUtils.getApplicationName();
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