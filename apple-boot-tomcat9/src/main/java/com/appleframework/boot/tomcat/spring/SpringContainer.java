package com.appleframework.boot.tomcat.spring;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.tomcat.EmbeddedTomcat;
import com.appleframework.boot.utils.ApplicationUtils;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class SpringContainer implements Container {

	private static Logger logger = Logger.getLogger(SpringContainer.class);
    
    public static final String DEFAULT_SPRING_CONFIG = "classpath*:META-INF/apple/apple-boot-tomcat-spring.xml";

    static ClassPathXmlApplicationContext context;
    
	private static long startTime = System.currentTimeMillis();
    
    public static ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public void start() {
        String configPath = DEFAULT_SPRING_CONFIG;
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        try {
        	EmbeddedTomcat server = context.getBean("tomcatServer", EmbeddedTomcat.class);
        	logger.warn("Start tomcat web context context= " + server.getContextPath() 
              		+ ";webapp path=" + server.getWebAppPath());        	
            server.startTomcat();
            logger.warn("启动成功");
        } catch (Exception e) {
        	logger.error("Failed to start tomcat server on " + ":" + ", cause: " + e.getMessage(), e);
        }
        startTime = System.currentTimeMillis();
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