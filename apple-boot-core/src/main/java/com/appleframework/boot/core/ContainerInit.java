package com.appleframework.boot.core;

import java.lang.management.ManagementFactory;
import java.util.Hashtable;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.appleframework.boot.core.log4j.Log4jContainer;
import com.appleframework.boot.core.log4j.LoggingConfig;
import com.appleframework.boot.core.monitor.MonitorConfig;
import com.appleframework.boot.core.monitor.MonitorContainer;

public class ContainerInit {

	private static Logger logger = Logger.getLogger(ContainerInit.class);

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
			mbs.registerMBean(new LoggingConfig(), oname);

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

}
