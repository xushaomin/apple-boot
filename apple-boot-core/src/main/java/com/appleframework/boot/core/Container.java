package com.appleframework.boot.core;

/**
 * Container. (SPI, Singleton, ThreadSafe)
 * 
 * @author cruise.xu
 */
public interface Container {
	
	public static final String DEFAULT_TYPE = "container";
	public static final String TYPE_KEY = "type";
	public static final String ID_KEY = "id";
		
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
    
    /**
     * startTime.
     */
    long getStartTime();

}