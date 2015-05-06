package com.appleframework.boot.jetty;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appleframework.boot.core.log4j.LoggingConfig;
import com.appleframework.config.core.EnvConfigurer;


/**
 * spring+Jetty的容器
 *
 * @author Cruise.Xu
 */
public class SpringJettyMain {

	private static Logger logger = Logger.getLogger(SpringJettyMain.class);

    public static void main(String[] args) {
    	for (int i = 0; i < args.length; i++) {
			String envArgs = args[i];
			if(envArgs.indexOf("env=") > -1) {
				String[] envs = envArgs.split("=");
				EnvConfigurer.env = envs[1];
				logger.warn("配置项：env=" + EnvConfigurer.env);
			}
		}
        ApplicationContext applicationContext = 
        		new ClassPathXmlApplicationContext("classpath*:META-INF/apple/apple-boot-jetty-spring.xml");
    	//ApplicationContext applicationContext = new FileSystemXmlApplicationContext("webapp/WEB-INF/apple/spring-jetty.xml");
        WebAppContext webAppContext = applicationContext.getBean("webAppContext", WebAppContext.class);
        //webAppContext.setMaxFormContentSize(9000000);
  
        logger.info("Start jetty web context context= " + webAppContext.getContextPath() + ";resource base=" + webAppContext.getResourceBase());
        try {
        	
        	//Log注册JMX
        	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName logOname = new ObjectName("com.appleframework.container:type=LogContainer");
			LoggingConfig logMbean = new LoggingConfig();
			if (mbs.isRegistered(logOname)) {
				mbs.unregisterMBean(logOname);
			}
			mbs.registerMBean(logMbean, logOname);
			
			//启动
            Server server = applicationContext.getBean("jettyServer", Server.class);
            server.start();
            server.join();
            logger.warn("启动成功");
        } catch (Exception e) {
        	logger.error("Failed to start jetty server on " + ":" + ", cause: " + e.getMessage(), e);
        }
    }


}