package com.appleframework.boot.jetty.spring;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.jetty.core.WebappContextAttribute;
import com.appleframework.boot.utils.ApplicationUtils;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class SpringContainer implements Container {

	private static Logger logger = Logger.getLogger(SpringContainer.class);
    
    public static final String DEFAULT_SPRING_CONFIG = "classpath*:META-INF/apple/apple-boot-jetty-spring.xml";

    static ClassPathXmlApplicationContext context;
    
	private static long startTime = System.currentTimeMillis();
    
    public static ClassPathXmlApplicationContext getContext() {
		return context;
	}

	@SuppressWarnings("rawtypes")
	public void start() {
        String configPath = DEFAULT_SPRING_CONFIG;
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        
        WebAppContext webAppContext = context.getBean("webAppContext", WebAppContext.class);
        //webAppContext.setMaxFormContentSize(-1);
        QueuedThreadPool threadPool = context.getBean("threadPool", QueuedThreadPool.class);
        ConnectorAttribute connectorAttribute = context.getBean("connectorAttribute", ConnectorAttribute.class);
        
        Iterator it = WebappContextAttribute.getIterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			if(key.startsWith("org.eclipse.jetty.webapp")
					|| key.startsWith("javax.servlet.context")) {
				Object value = entry.getValue();
				webAppContext.setAttribute(key, value);
			}
		}
		
        logger.warn("Start jetty web context maxFormContentSize= " + webAppContext.getMaxFormContentSize()); 
        logger.warn("Start jetty web context context= " + webAppContext.getContextPath() + ";resource base=" + webAppContext.getResourceBase());
        startTime = System.currentTimeMillis();
        try {
        	
            Server server = new Server(threadPool);
            ServerConnector connector = null;
            if(null != connectorAttribute.getAcceptors() && null != connectorAttribute.getSelectors()) {
            	connector = new ServerConnector(server, connectorAttribute.getAcceptors(), connectorAttribute.getSelectors());
            }
            else {
            	connector = new ServerConnector(server);
            }
            connector.setPort(connectorAttribute.getPort());
			if (null != connectorAttribute.getAcceptQueueSize()) {
				connector.setAcceptQueueSize(connectorAttribute.getAcceptQueueSize());
			}

            server.addConnector(connector);
            server.setHandler(webAppContext);
            server.setStopAtShutdown(true);
            
            while (it.hasNext()) {
    			Map.Entry entry = (Map.Entry) it.next();
    			String key = (String) entry.getKey();
    			if(key.startsWith("org.eclipse.jetty.server")) {
    				Object value = entry.getValue();
    				server.setAttribute(key, value);
    			}
    		}
            
            // Setup JMX
			MBeanContainer mbeanContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
            server.getServer().addEventListener(mbeanContainer);
            server.addBean(mbeanContainer);
            
            server.start();
            server.join();
            logger.warn("启动成功");
        } catch (Exception e) {
        	logger.error("Failed to start jetty server on " + ":" + ", cause: " + e.getMessage(), e);
        }
    }

    public void stop() {
        try {
            if (context != null) {
                context.stop();
                context.close();
                context = null;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    public void restart() {
        try {
            this.stop();
            this.start();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    public boolean isRunning() {
    	if (context != null) {
    		return context.isRunning();
    	}
    	else {
    		return false;
    	}
    }

	@Override
	public String getName() {
    	return ApplicationUtils.getApplicationName();
	}
    
	@Override
	public String getType() {
		return "JettyContainer";
	}

	@Override
	public long getStartTime() {
		return startTime;
	}	

}