package com.appleframework.boot.core.logging.logback;

import java.util.ArrayList;
import java.util.List;

import com.appleframework.boot.jmx.LoggingConfigMBean;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

public class LogbackConfig implements LoggingConfigMBean {

	public String[] getLoggers(String filter) {
		LoggerContext loggerContext = LogbackUtils.getLoggerContext();
		List<Logger> loggerList = loggerContext.getLoggerList();
		List<String> resultList = new ArrayList<String>();
		for (Logger logger : loggerList) {
			if (filter == null || (filter != null && logger.getName().contains(filter))) {
				resultList.add(logger.getName());
			}
		}
		return (String[]) resultList.toArray(new String[] {});
	}

	public void assignInfoLevel(String target) {
		assignLogLevel(target, Level.INFO);
	}

	public void assignWarnLevel(String target) {
		assignLogLevel(target, Level.WARN);
	}

	public void assignErrorLevel(String target) {
		assignLogLevel(target, Level.ERROR);
	}

	public void assignDebugLevel(String target) {
		assignLogLevel(target, Level.DEBUG);
	}

	public void assignFatalLevel(String target) {
		// assignLogLevel(target, Level.FATAL);
	}

	public void deactivateLogging(String target) {
		assignLogLevel(target, Level.OFF);
	}

	public void assignTraceLevel(String target) {
		assignLogLevel(target, Level.TRACE);
	}

	public void assignLevel(String target, Level level) {
		assignLogLevel(target, level);
	}

	public void assignLevel(String logLevel) {
		Level level = Level.toLevel(logLevel);
		this.assignLogLevel(null, level);
	}

	private void assignLogLevel(String target, Level level) {
		LoggerContext loggerContext = LogbackUtils.getLoggerContext();
		List<Logger> loggerList = loggerContext.getLoggerList();

		if (null == target || target.trim().length() == 0) {
			for (Logger logger : loggerList) {
				logger.setLevel(level);
			}
		} else {
			for (Logger logger : loggerList) {
				if (logger.getName().contains(target)) {
					logger.setLevel(level);
				}
			}
		}
	}

	public void resetConfiguration() {
		LogbackUtils.getLoggerContext().reset();
	}

	public String printLogConfig() {
		LoggerContext loggerContext = LogbackUtils.getLoggerContext();
		List<Logger> loggerList = loggerContext.getLoggerList();
		StringBuffer buffer = new StringBuffer();
		for (Logger logger : loggerList) {
			buffer.append(logger.getName());
			buffer.append("->");
			buffer.append(logger.getLevel().toString());
			buffer.append("\n");
		}
		return buffer.toString();
	}

	public void assignInfoLevel() {
		assignLogLevel(null, Level.INFO);
	}

	public void assignWarnLevel() {
		assignLogLevel(null, Level.WARN);
	}

	public void assignErrorLevel() {
		assignLogLevel(null, Level.ERROR);
	}

	public void assignDebugLevel() {
		assignLogLevel(null, Level.DEBUG);
	}

	public void assignFatalLevel() {
		// assignLogLevel(null, Level.FATAL);
	}

	public void deactivateLogging() {
		assignLogLevel(null, Level.OFF);
	}

	public void assignTraceLevel() {
		assignLogLevel(null, Level.TRACE);
	}

	@Override
	public String getRootLoggerLevel() {
		return LogbackUtils.getLoggerContext().getName();
	}

}
