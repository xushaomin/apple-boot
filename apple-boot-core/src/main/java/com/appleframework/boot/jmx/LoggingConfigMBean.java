package com.appleframework.boot.jmx;

import org.apache.log4j.Level;

public interface LoggingConfigMBean {

	/**
	 * 
	 * @param filter returns only loggers, which contain the filter string
	 * @return all available loggers
	 */
	public String[] getLoggers(String filter);
	
	/**
	 * assigns the {@link Level#INFO} to the given class
	 * @param target the FQCN of the class
	 */
	public void assignInfoLevel(String target);

	/**
	 * assigns the {@link Level#WARN} to the given class
	 * @param target the FQCN of the class
	 */
	public void assignWarnLevel(String target);

	/**
	 * assigns the {@link Level#ERROR} to the given class
	 * @param target the FQCN of the class
	 */
	public void assignErrorLevel(String target);

	/**
	 * assigns the {@link Level#DEBUG} to the given class
	 * @param target the FQCN of the class
	 */
	public void assignDebugLevel(String target);
	
	/**
	 * assigns the {@link Level#FATAL} to the given class
	 * @param target the FQCN of the class
	 */
	public void assignFatalLevel(String target);
	
	/**
	 * assigns the {@link Level#TRACE} to the given class
	 * @param target the FQCN of the class
	 */
	public void assignTraceLevel(String target);
	
	/**
	 * deactivates the logging of the given class
	 * @param target the FQCN of the class
	 */
	public void deactivateLogging(String target);
	
	/**
	 * reloads the log4j configuration from the <code>log4j.properties</code> file in the classpath 
	 */
	public void resetConfiguration();

	/**
	 * 
	 * @return the log4j configuration from the <code>log4j.properties</code> file in the classpath 
	 */
	public String printLogConfig();
	
	/**
	 * assigns the {@link Level#level} to the target
	 */
	//public void assignLevel(String target, Level level);
	
	/**
	 * assigns the {@link Level#level} to the RootLogger
	 */
	public void assignLevel(String level);
	
	/**
	 * assigns the {@link Level#INFO} to the RootLogger
	 */
	public void assignInfoLevel();

	/**
	 * assigns the {@link Level#WARN} to the RootLogger
	 */
	public void assignWarnLevel();

	/**
	 * assigns the {@link Level#ERROR} to the RootLogger
	 */
	public void assignErrorLevel();

	/**
	 * assigns the {@link Level#DEBUG} to the RootLogger
	 */
	public void assignDebugLevel();
	
	/**
	 * assigns the {@link Level#FATAL} to the RootLogger
	 */
	public void assignFatalLevel();
	
	/**
	 * assigns the {@link Level#TRACE} to the RootLogger
	 */
	public void assignTraceLevel();
	
	/**
	 * deactivates the logging of the RootLogger
	 */
	public void deactivateLogging();
	
	/**
	 * get the level of the RootLogger
	 */
	public String getRootLoggerLevel();
}
