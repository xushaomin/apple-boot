package com.appleframework.boot;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.core.logging.LoggingContainerFactory;
import com.appleframework.boot.core.logging.log4j.Log4jConfig;
import com.appleframework.boot.core.logging.log4j.Log4jContainer;
import com.appleframework.boot.core.monitor.MonitorConfig;
import com.appleframework.boot.core.monitor.MonitorContainer;
import com.appleframework.boot.core.monitor.MonitorContainerFactory;

public class BootAgent {
	
	/**
	 * 该方法在main方法之前运行，与main方法运行在同一个JVM中 并被同一个System ClassLoader装载
	 * 被统一的安全策略(security policy)和上下文(context)管理
	 *
	 * @param agentOps
	 * @param inst
	 * @author Cruise
	 * @create 2017年3月30日
	 */
	public static void premain(String agentOps, Instrumentation inst) {
		premain(agentOps);
	}

	/**
	 * 如果不存在 premain(String agentOps, Instrumentation inst) 
	 * 则会执行 premain(String agentOps)
	 *
	 * @param agentOps
	 * @author Cruise
	 * @create 2017年3月30日
	 */
	public static void premain(String agentOps) {
		try {
			
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

			final List<Container> containers = new ArrayList<Container>();
            containers.add(MonitorContainerFactory.getContainer());
			containers.add(LoggingContainerFactory.getContainer());

			for (Container container : containers) {
                try {
					
					Hashtable<String, String> properties = new Hashtable<String, String>();

					properties.put(Container.TYPE_KEY, Container.DEFAULT_TYPE);
					properties.put(Container.ID_KEY, container.getType());
					
					ObjectName oname = ObjectName.getInstance("com.appleframework", properties);
					Object mbean = null;
					if(container instanceof Log4jContainer) {
						mbean = new Log4jConfig();
					}
					else if(container instanceof MonitorContainer) {
						mbean = new MonitorConfig();
					}
					else {
						mbean = null;
					}
					
					if(null == mbean) {
						continue;
					}
					
					if (mbs.isRegistered(oname)) {
						mbs.unregisterMBean(oname);
					}
					mbs.registerMBean(mbean, oname);

				} catch (Exception e) {
					System.out.println("注册JMX服务出错：" + e.getMessage());
				}
                System.out.println("服务 " + container.getType() + " 启动中!");
                container.start();
                
            }
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " 所有服务启动成功!");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
}
