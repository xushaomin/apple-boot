package com.appleframework.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.boot.core.Container;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class SpringContainer implements Container {

	private static Logger logger = LoggerFactory.getLogger(SpringContainer.class);
        
	private static long startTime = System.currentTimeMillis();
   
	private static boolean isRunning = true;

	public void start() {
		logger.error("start.......");
		isRunning = true;
	}

    public void stop() {
    	logger.error("stop.......");
    	isRunning = false;
    }
    
    public void restart() {
    	logger.error("restart.......");
    }
    
    public boolean isRunning() {
    	return isRunning;
    }

	@Override
	public String getName() {
    	return "SpringContianer";
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