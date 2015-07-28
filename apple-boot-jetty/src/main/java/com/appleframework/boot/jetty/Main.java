package com.appleframework.boot.jetty;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.core.log4j.Log4jContainer;
import com.appleframework.boot.core.log4j.LoggingConfig;
import com.appleframework.boot.core.monitor.MonitorConfig;
import com.appleframework.boot.core.monitor.MonitorContainer;
import com.appleframework.boot.jetty.spring.SpringContainer;
import com.appleframework.boot.jetty.spring.SpringContainerManager;
import com.appleframework.config.core.EnvConfigurer;

/**
 * spring+Jetty的容器
 *
 * @author Cruise.Xu
 */
public class Main {

    public static final String SHUTDOWN_HOOK_KEY = "shutdown.hook";
        
    private static Logger logger = Logger.getLogger(Main.class);
    
    protected static volatile boolean running = true;

	public static void main(String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				String envArgs = args[i];
				if (envArgs.indexOf("env=") > -1) {
					String[] envs = envArgs.split("=");
					EnvConfigurer.env = envs[1];
					logger.warn("配置项：env=" + EnvConfigurer.env);
				}
			}

			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

			final List<Container> containers = new ArrayList<Container>();
			Container springContainer = new SpringContainer();
			Container log4jContainer = new Log4jContainer();
            Container monitorContainer = new MonitorContainer();
            containers.add(log4jContainer);
            containers.add(monitorContainer);
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
					if(container instanceof SpringContainer) {
						SpringContainerManager mbean = new SpringContainerManager();

						if (mbs.isRegistered(oname)) {
							mbs.unregisterMBean(oname);
						}
						mbs.registerMBean(mbean, oname);
					}
					else if(container instanceof Log4jContainer) {
						LoggingConfig mbean = new LoggingConfig();

						if (mbs.isRegistered(oname)) {
							mbs.unregisterMBean(oname);
						}
						mbs.registerMBean(mbean, oname);
					}
					else if(container instanceof MonitorContainer) {
						MonitorConfig mbean = new MonitorConfig();

						if (mbs.isRegistered(oname)) {
							mbs.unregisterMBean(oname);
						}
						mbs.registerMBean(mbean, oname);
					}
					else {
						
					}
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