package com.appleframework.boot.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.utils.ApplicationUtils;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author cruise.xu
 */
public class SpringContainer implements Container {

	private static Logger logger = LoggerFactory.getLogger(SpringContainer.class);
    
	private static final String DEFAULT_SPRING_CONFIG = "classpath*:config/*.xml";

    static ClassPathXmlApplicationContext context;
    
	private static long startTime = System.currentTimeMillis();
    
    public static ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public void start() {
        String configPath = DEFAULT_SPRING_CONFIG;
        
        String springConfigFile = System.getProperty("spring.config.file");
        if(null != springConfigFile) {
        	configPath = springConfigFile;
        }
        
        String springConfigPath = System.getProperty("spring.config.path");
        if(null != springConfigPath) {
        	configPath = springConfigPath + "/*.xml";
        }
        
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        context.start();
        
        String applicationName = ApplicationUtils.getApplicationName();
        if(null != applicationName) {
        	context.setDisplayName(applicationName);
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

	@Override
	public void restart() {
		try {
			if (context != null) {
                context.stop();
                context.start();
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
	}

	@Override
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