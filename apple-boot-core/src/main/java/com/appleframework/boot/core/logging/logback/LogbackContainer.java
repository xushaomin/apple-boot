package com.appleframework.boot.core.logging.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.boot.core.Container;

import ch.qos.logback.classic.LoggerContext;

public class LogbackContainer implements Container {

	private static Logger logger = LoggerFactory.getLogger(LogbackContainer.class);
	
	private static long startTime = System.currentTimeMillis();

	@Override
	public void start() {
		logger.warn("LogbackContainer start");
		startTime = System.currentTimeMillis();
	}

	@Override
	public void stop() {
		logger.warn("LogbackContainer stop");
	}

	@Override
	public void restart() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.stop();
		loggerContext.start();
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

	public long getStartTime() {
		return startTime;
	}

}
