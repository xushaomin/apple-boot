package com.appleframework.boot.core.logging.log4j;

import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.appleframework.boot.core.Container;


public class Log4jContainer implements Container {

	private static Logger logger = Logger.getLogger(Log4jContainer.class);
	
	private static long startTime = System.currentTimeMillis();

	@Override
	public void start() {
		logger.warn("Log4jContainer start");
		startTime = System.currentTimeMillis();
	}

	@Override
	public void stop() {
		logger.warn("Log4jContainer stop");
	}

	@Override
	public void restart() {
		ClassLoader cl = getClass().getClassLoader();
		LogManager.resetConfiguration();
		URL log4jUrl = cl.getResource("log4j.properties");
		if (log4jUrl != null) {
			PropertyConfigurator.configure(log4jUrl);
		}
		else {
			log4jUrl = cl.getResource("log4j.xml");
			DOMConfigurator.configure(log4jUrl); 
		}
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public String getName() {
		return "Log4jContainer";
	}

	@Override
	public String getType() {
		return "LoggingContainer";
	}

	public long getStartTime() {
		return startTime;
	}

}
