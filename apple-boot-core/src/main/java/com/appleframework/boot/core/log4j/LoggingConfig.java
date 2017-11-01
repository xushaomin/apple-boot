package com.appleframework.boot.core.log4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.config.PropertyPrinter;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.xml.DOMConfigurator;

public class LoggingConfig implements LoggingConfigMBean {
	
	@SuppressWarnings("unchecked")
	public String[] getLoggers(String filter) {
		LoggerRepository r = LogManager.getLoggerRepository();
		Enumeration<Logger> enumList = r.getCurrentLoggers();
		Logger logger = null;
		List<String> resultList = new ArrayList<String>();
		while (enumList.hasMoreElements()) {
			logger = (Logger) enumList.nextElement();
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
		assignLogLevel(target, Level.FATAL);
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
		if(null == target || target.trim().length() == 0) {
			Logger logger = LogManager.getLoggerRepository().getRootLogger();
			logger.setLevel(level);
		}
		else {
			String message = level.toString() + " for '" + target + "'";
			Logger existingLogger = LogManager.exists(target);
			if(existingLogger != null) {
				Level currentLevel = existingLogger.getLevel();
				if(currentLevel == null) {
					message = "initial to " + message;
				} else {
					message = "from " + currentLevel.toString() + " to " + message;
				}
			}
			LogManager.getLogger(target).setLevel(level);
		}
	}

	public void resetConfiguration() {
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

	public String printLog4jConfig() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		PropertyPrinter pp = new PropertyPrinter(pw);
		pp.print(pw);
		return sw.toString();
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
		assignLogLevel(null, Level.FATAL);
	}

	public void deactivateLogging() {
		assignLogLevel(null, Level.OFF);
	}

	public void assignTraceLevel() {
		assignLogLevel(null, Level.TRACE);
	}

	@Override
	public String getRootLoggerLevel() {
		return LogManager.getLoggerRepository().getRootLogger().getLevel().toString();
	}

	
}
