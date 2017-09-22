package com.appleframework.boot.jetty.container;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.jetty.core.JettyAttribute;
import com.appleframework.boot.utils.ApplicationUtils;

/**
 * JettyContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class JettyContainer implements Container {

	private static Logger logger = Logger.getLogger(JettyContainer.class);
        
	private static long startTime = System.currentTimeMillis();	
	
	private Server server;
	
	@SuppressWarnings("rawtypes")
	public void start() {
        
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath(JettyAttribute.getValue(JettyAttribute.WEB_CONTEXT, "/"));
        webAppContext.setResourceBase("./webapp");
        webAppContext.setParentLoaderPriority(true);
        webAppContext.setDescriptor("webapp\\WEB-INF\\web.xml");
        webAppContext.setMaxFormContentSize(-1);
        
        int size  = webAppContext.getMaxFormContentSize();
        
        Iterator<?> it = JettyAttribute.getIterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			if(key.startsWith("org.eclipse.jetty.webapp")
					|| key.startsWith("javax.servlet.context")) {
				Object value = entry.getValue();
				webAppContext.setAttribute(key, value);
				
				try {
					if(key.equals("org.eclipse.jetty.webapp.basetempdir")) {
						File dir = new File(value.toString());
						if(!dir.exists() && dir.isDirectory()) {
							dir.mkdirs();
						}
					}
				} catch (Exception e) {
					logger.error("create basetempdir fail :" + e.getMessage());
				}
			}
		}
		
        logger.warn("Start jetty web context maxFormContentSize= " + webAppContext.getMaxFormContentSize()); 
        logger.warn("Start jetty web context context= " + webAppContext.getContextPath() + ";resource base=" + webAppContext.getResourceBase());
        startTime = System.currentTimeMillis();
        try {
        	
        	QueuedThreadPool threadPool = new QueuedThreadPool();
        	threadPool.setMinThreads(JettyAttribute.getInteger(JettyAttribute.MIN_THREADS, 100));
        	threadPool.setMaxQueued(JettyAttribute.getInteger(JettyAttribute.MAX_THREADS, 1000));
        	threadPool.setMaxQueued(JettyAttribute.getInteger(JettyAttribute.MAX_QUEUED, -1));
        	
        	SelectChannelConnector connector = new SelectChannelConnector();
        	connector.setPort(JettyAttribute.getInteger(JettyAttribute.WEB_PORT, 8080));

            server = new Server();
            server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", size);
            server.setThreadPool(threadPool);
            server.setHandler(webAppContext);
            server.addConnector(connector);

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
            server.getContainer().addEventListener(mbeanContainer);
            server.addBean(mbeanContainer);
            
            server.start();
            server.join();
            logger.warn("Apple Boot " + Server.getVersion() + " Success !");
        } catch (Exception e) {
        	logger.error("Failed to start jetty server on " + ":" + ", cause: " + e.getMessage(), e);
        }
    }

    public void stop() {
        try {
            if (server != null) {
            	server.stop();
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
    	if (server != null) {
    		return server.isRunning();
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