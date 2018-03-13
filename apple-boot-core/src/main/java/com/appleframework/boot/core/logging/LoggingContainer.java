package com.appleframework.boot.core.logging;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.core.logging.log4j.Log4jContainer;
import com.appleframework.boot.core.logging.log4j.Log4jUtils;
import com.appleframework.boot.core.logging.logback.LogbackContainer;
import com.appleframework.boot.core.logging.logback.LogbackUtils;

/**
 * LoggingContainer
 * 
 * @author cruise.xu
 */
public class LoggingContainer {
	
	private static String LOG_CONTAINER = "log4j";

	public static Container getLoggingContainer(String logContainer) {
		LOG_CONTAINER = logContainer;
		if (null == logContainer) {
			return new Log4jContainer();
		} else if (logContainer.equals("log4j")) {
			return new Log4jContainer();
		} else if (logContainer.equals("slf4j")) {
			return new LogbackContainer();
		} else if (logContainer.equals("logback")) {
			return new LogbackContainer();
		} else {
			return new Log4jContainer();
		}
	}
	
	public static String getRootLoggerLevelString() {
		if("log4j".equals(LOG_CONTAINER)) {
			return Log4jUtils.getRootLoggerLevelString();
		}
		else if("slf4j".equals(LOG_CONTAINER)) {
			return LogbackUtils.getRootLoggerLevelString();
		}
		else if("logback".equals(LOG_CONTAINER)) {
			return LogbackUtils.getRootLoggerLevelString();
		}
		else {
			return null;
		}
	}
}