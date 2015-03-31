package com.appleframework.boot.spring;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appleframework.boot.core.Container;
import com.appleframework.config.core.PropertyConfigurer;


/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author cruise.xu
 */
public class SpringContainer implements Container {

	private static Logger logger = Logger.getLogger(SpringContainer.class);
    
    public static final String DEFAULT_SPRING_CONFIG = "classpath*:config/*.xml";

    static ClassPathXmlApplicationContext context;
    
    public static ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public void start() {
        String configPath = DEFAULT_SPRING_CONFIG;
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        context.start();
        
        String applicationName = PropertyConfigurer.getString(Container.APPLICATION_NAME_KEY);
        if(null != applicationName) {
        	context.setDisplayName(applicationName);
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
		if (context != null) {
			return context.getDisplayName();
    	}
    	else {
    		return PropertyConfigurer.getString(Container.APPLICATION_NAME_KEY);
    	}
	}
    
	@Override
	public String getType() {
		return "SpringContainer";
	}

}