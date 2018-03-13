package com.appleframework.boot;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.core.logging.LoggingContainer;
import com.appleframework.boot.core.logging.log4j.Log4jConfig;
import com.appleframework.boot.core.logging.log4j.Log4jContainer;
import com.appleframework.boot.core.monitor.MonitorConfig;
import com.appleframework.boot.core.monitor.MonitorContainer;
import com.appleframework.boot.jetty.spring.SpringContainer;
import com.appleframework.boot.jetty.spring.SpringContainerManager;

/**
 * spring+Jetty的容器
 *
 * @author Cruise.Xu
 */
public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static final String SHUTDOWN_HOOK_KEY = "shutdown.hook";
        
    protected static volatile boolean running = true;

	public static void main(String[] args) {
		try {
			StartUpInit.init(args);

			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

			final List<Container> containers = new ArrayList<Container>();
            containers.add(new MonitorContainer());
            
            String logContainer = System.getProperty("log-container");
			containers.add(LoggingContainer.getLoggingContainer(logContainer));
            
            SpringContainer springContainer = new SpringContainer();
            containers.add(springContainer);

			if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						for (Container container : containers) {
							try {
								container.stop();
								logger.info("Server " + container.getName() + " stopped!");
							} catch (Throwable t) {
								logger.error(t.getMessage(), t);
							}
							synchronized (Main.class) {
								running = false;
								Main.class.notify();
							}
						}
					}
				});
			}

			for (Container container : containers) {
                try {
					
					Hashtable<String, String> properties = new Hashtable<String, String>();

					properties.put(Container.TYPE_KEY, Container.DEFAULT_TYPE);
					properties.put(Container.ID_KEY, container.getType());
					
					ObjectName oname = ObjectName.getInstance("com.appleframework", properties);
					Object mbean = null;
					if(container instanceof SpringContainer) {
						SpringContainerManager manager = new SpringContainerManager();
						manager.setSpringContainer(container);
						mbean = manager;
					}
					else if(container instanceof Log4jContainer) {
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
					logger.error("注册JMX服务出错：" + e.getMessage(), e);
				}
                logger.warn("服务 " + container.getType() + " 启动中!");
                container.start();
                
            }
			logger.warn(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " 所有服务启动成功!");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			System.exit(1);
		}
		synchronized (Main.class) {
			while (running) {
				try {
					Main.class.wait();
				} catch (Throwable e) {
				}
			}
		}
	}
    
}