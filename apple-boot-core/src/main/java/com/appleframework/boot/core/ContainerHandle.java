package com.appleframework.boot.core;

import java.lang.management.ManagementFactory;
import java.util.Hashtable;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.boot.core.logging.log4j.Log4jConfig;
import com.appleframework.boot.core.logging.log4j.Log4jContainer;
import com.appleframework.boot.core.logging.logback.LogbackConfig;
import com.appleframework.boot.core.monitor.MonitorConfig;
import com.appleframework.boot.core.monitor.MonitorContainer;
import com.appleframework.boot.jmx.ContainerManagerUtils;

public class ContainerHandle {

	private static Logger logger = LoggerFactory.getLogger(ContainerHandle.class);

	public static void initLog4jContainer() {
		try {

			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

			Container container = new Log4jContainer();
			container.start();

			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put(Container.TYPE_KEY, Container.DEFAULT_TYPE);
			properties.put(Container.ID_KEY, container.getType());

			ObjectName oname = ObjectName.getInstance("com.appleframework", properties);

			if (mbs.isRegistered(oname)) {
				mbs.unregisterMBean(oname);
			}
			mbs.registerMBean(new Log4jConfig(), oname);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void initMonitorContainer() {
		try {

			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			
			Container container = new MonitorContainer();
			container.start();

			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put(Container.TYPE_KEY, Container.DEFAULT_TYPE);
			properties.put(Container.ID_KEY, container.getType());

			ObjectName oname = ObjectName.getInstance("com.appleframework", properties);
			if (mbs.isRegistered(oname)) {
				mbs.unregisterMBean(oname);
			}
			mbs.registerMBean(new MonitorConfig(), oname);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void jmx(List<Container> containers) {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		for (Container container : containers) {
			try {

				Hashtable<String, String> properties = new Hashtable<String, String>();
				properties.put(Container.TYPE_KEY, Container.DEFAULT_TYPE);
				properties.put(Container.ID_KEY, container.getType());

				ObjectName oname = ObjectName.getInstance("com.appleframework", properties);
				Object mbean = null;

				if (container.getType().equals("SpringContainer")) {
					mbean = ContainerManagerUtils.instance(container);
				} else if (container.getType().equals("JavaContainer")) {
					mbean = ContainerManagerUtils.instance(container);
				} else if (container.getType().equals("MonitorContainer")) {
					mbean = new MonitorConfig();
				} else if (container.getType().equals("LoggingContainer")) {
					if (container.getName().equals("LogbackContainer")) {
						mbean = new LogbackConfig();
					} else {
						mbean = new Log4jConfig();
					}
				} else {
					mbean = null;
				}

				if (null == mbean) {
					continue;
				}

				if (mbs.isRegistered(oname)) {
					mbs.unregisterMBean(oname);
				}
				mbs.registerMBean(mbean, oname);

			} catch (Exception e) {
				logger.error("Registe JMX Service Error：" + e.getMessage(), e);
			}
			logger.warn("JMX Service " + container.getType() + " Registe Success!");
		}

	}
	
	public static void start(List<Container> containers) {
		for (Container container : containers) {
            logger.warn("Service " + container.getType() + " start ...!");
            container.start();
            logger.warn("Service " + container.getType() + " end ...!");
        }
	}

}
