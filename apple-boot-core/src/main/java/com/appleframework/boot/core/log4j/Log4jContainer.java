package com.appleframework.boot.core.log4j;

import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.appleframework.boot.core.Container;


public class Log4jContainer implements Container {

	private static Logger logger = Logger.getLogger(Log4jContainer.class);

	@Override
	public void start() {
		logger.warn("Log4jContainer start");
	}

	@Override
	public void stop() {
		logger.warn("Log4jContainer stop");
	}

	@Override
	public void restart() {
		ClassLoader cl = getClass().getClassLoader();
		LogManager.resetConfiguration();
		URL log4jprops = cl.getResource("log4j.properties");
		if (log4jprops != null) {
			PropertyConfigurator.configure(log4jprops);
		}
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public String getName() {
		return "LogContainer";
	}

	@Override
	public String getType() {
		return "LogContainer";
	}

}
