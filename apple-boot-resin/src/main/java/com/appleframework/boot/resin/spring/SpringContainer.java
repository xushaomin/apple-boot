package com.appleframework.boot.resin.spring;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.utils.SystemPropertiesUtils;
import com.caucho.resin.HttpEmbed;
import com.caucho.resin.ResinEmbed;
import com.caucho.resin.WebAppEmbed;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class SpringContainer implements Container {

	private static Logger logger = Logger.getLogger(SpringContainer.class);
    
    public static final String DEFAULT_SPRING_CONFIG = "classpath*:META-INF/apple/apple-boot-resin-spring.xml";

    static ClassPathXmlApplicationContext context;
    
	private static long startTime = System.currentTimeMillis();
    
    public static ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public void start() {
        String configPath = DEFAULT_SPRING_CONFIG;
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        
        WebAppEmbed webApp = context.getBean("webAppEmbed", WebAppEmbed.class);
        ServerPort serverPort = context.getBean("serverPort", ServerPort.class);
        
        //logger.warn("Start resin web context maxFormContentSize= " + webAppContext.getMaxFormContentSize()); 
        logger.warn("Start resin web context context= " + webApp.getContextPath() + ";root directory=" + webApp.getRootDirectory());
        startTime = System.currentTimeMillis();
        try {
           
            ResinEmbed resin = new ResinEmbed();

            HttpEmbed http = new HttpEmbed(serverPort.getPort());
            resin.addPort(http);

            resin.addWebApp(webApp);

            resin.start();
            resin.join();
            
            logger.warn("启动成功");
        } catch (Exception e) {
        	logger.error("Failed to start resin server on " + ":" + ", cause: " + e.getMessage(), e);
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
    	return SystemPropertiesUtils.getApplicationName();
	}
    
	@Override
	public String getType() {
		return "ResinContainer";
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

}