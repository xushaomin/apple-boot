package com.appleframework.boot.core.logging.logback;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;

public class LogbackUtils {
	
	public static LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
	}

}