package com.appleframework.boot.core;

/**
 * Container. (SPI, Singleton, ThreadSafe)
 * 
 * @author cruise.xu
 */
public interface Container {
		
	/**
     * name.
     */
	public String getName();
    
	/**
     * type.
     */
	public String getType();
    
    /**
     * start.
     */
    void start();
    
    /**
     * stop.
     */
    void stop();
    
    /**
     * restart.
     */
    void restart();
    
    /**
     * status.
     */
    boolean isRunning();

}