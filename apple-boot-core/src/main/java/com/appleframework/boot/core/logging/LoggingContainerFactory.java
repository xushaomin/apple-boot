package com.appleframework.boot.core.logging;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.core.ContainerFactory;
import com.appleframework.boot.core.logging.log4j.Log4jContainer;
import com.appleframework.boot.core.logging.log4j.Log4jUtils;
import com.appleframework.boot.core.logging.logback.LogbackContainer;
import com.appleframework.boot.core.logging.logback.LogbackUtils;

/**
 * LoggingContainer
 * 
 * @author cruise.xu
 */
public class LoggingContainerFactory extends ContainerFactory {
	
	private static String LOG_CONTAINER = "log4j";
	
	public static Container getContainer() {
		String logContainer = System.getProperty("log-container");
		if (null == logContainer) {
			return create(Log4jContainer.class);
		} else if (logContainer.equals("log4j")) {
			return create(Log4jContainer.class);
		} else if (logContainer.equals("slf4j")) {
			return create(LogbackContainer.class);
		} else if (logContainer.equals("logback")) {
			return create(LogbackContainer.class);
		} else {
			return create(Log4jContainer.class);
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