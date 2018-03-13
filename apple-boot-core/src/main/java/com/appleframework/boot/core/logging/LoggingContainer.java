package com.appleframework.boot.core.logging;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.core.logging.log4j.Log4jContainer;
import com.appleframework.boot.core.logging.logback.LogbackContainer;

/**
 * LoggingContainer
 * 
 * @author cruise.xu
 */
public class LoggingContainer {

	public static Container getLoggingContainer(String logContainer) {
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
}