package com.appleframework.boot.jetty.spring;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.jetty.core.WebappContextAttribute;
import com.appleframework.boot.utils.ApplicationUtils;
import com.appleframework.boot.utils.ResourceUtils;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class SpringContainer implements Container {

	private static Logger logger = LoggerFactory.getLogger(SpringContainer.class);
    
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
        QueuedThreadPool threadPool = context.getBean("threadPool", QueuedThreadPool.class);
        ConnectorAttribute connectorAttribute = context.getBean("connectorAttribute", ConnectorAttribute.class);
        
        String resourceBase = webAppContext.getResourceBase();
        String devFlag = System.getProperty("jetty.dev");
        if(!ResourceUtils.isExistResourceBase(resourceBase) || "true".equalsIgnoreCase(devFlag)) {
        	resourceBase = resourceBase.replaceAll("webapp", "src/main/webapp");
        	webAppContext.setResourceBase(resourceBase);
        }
        
        Iterator it = WebappContextAttribute.getIterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			logger.warn("The jetty attrbiute {}={}", key, value);
			if(key.startsWith("org.eclipse.jetty.webapp")
					|| key.startsWith("javax.servlet.context")) {
				webAppContext.setAttribute(key, value);
				
				try {
					if("org.eclipse.jetty.webapp.basetempdir".equals(key)) {
						File dir = new File(value.toString());
						if(!dir.exists()) {
							dir.mkdirs();
						}
					}
				} catch (Exception e) {
					logger.error("create basetempdir fail :" + e.getMessage());
				}
			}
		}
		
        logger.warn("Start jetty web context maxFormContentSize= " + webAppContext.getMaxFormContentSize()); 
        logger.warn("Start jetty web context context= " + webAppContext.getContextPath() + ";"
        		+ "resource base=" + webAppContext.getResourceBase());
        startTime = System.currentTimeMillis();
        try {
        	
            Server server = new Server(threadPool);
            ServerConnector connector = null;
            if(connectorAttribute.getAcceptors() > 0 && connectorAttribute.getSelectors() > 0) {
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
		return "SpringContainer";
	}

	@Override
	public long getStartTime() {
		return startTime;
	}	

}