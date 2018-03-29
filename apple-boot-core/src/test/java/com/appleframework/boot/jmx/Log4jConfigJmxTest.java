package com.appleframework.boot.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Hashtable;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.core.logging.log4j.Log4jConfig;

public class Log4jConfigJmxTest {
	
	public static void main(String[] args) {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		Hashtable<String, String> properties = new Hashtable<String, String>();

		properties.put(Container.TYPE_KEY, Container.DEFAULT_TYPE);
		properties.put(Container.ID_KEY, "container");
		
		try {
			ObjectName oname = ObjectName.getInstance("com.appleframework", properties);
			Object mbean = new Log4jConfig();
			if (mbs.isRegistered(oname)) {
				mbs.unregisterMBean(oname);
			}
			mbs.registerMBean(mbean, oname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
