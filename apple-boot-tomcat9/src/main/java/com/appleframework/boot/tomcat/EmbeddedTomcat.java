package com.appleframework.boot.tomcat;

import java.net.URL;

import org.apache.catalina.Executor;
import org.apache.catalina.startup.Tomcat;
import org.apache.log4j.Logger;

/**
 * @author cruise.xu
 * */
public class EmbeddedTomcat {

	private static Logger logger = Logger.getLogger(EmbeddedTomcat.class);

	private static Tomcat tomcat = null;
	private static String ENCODING = "UTF-8";
	private static String CONTEXT_PATH = "/";
	
	// 启动嵌入式Tomcat服务器
	private static int TOMCAT_PORT = 8080;

	private int tomcatPort = TOMCAT_PORT;
	private String contextPath;
	private String webAppPath;
	private Executor executor;

	public void startTomcat() throws Exception {
		try {
			long startTime = System.currentTimeMillis();
			tomcat = new Tomcat();
			// 设置Tomcat的工作目录
			//tomcat.setBaseDir(tomcatHome);
			tomcat.setPort(tomcatPort);
			tomcat.addWebapp(contextPath, getWebAppPath());
			// tomcat.enableNaming();//执行这句才能支持JDNI查找
			tomcat.getConnector().setURIEncoding(ENCODING);
			
			if(null != executor) {
				tomcat.getService().addExecutor(executor);
			}
			
			tomcat.start();
			logger.warn("Tomcat started in " + (System.currentTimeMillis() - startTime) + " ms.");
			tomcat.getServer().await();// 让服务器一直跑
			
			/*Runtime.getRuntime().addShutdownHook(new Thread() {
	            public void run() {
	                stop();
	            }
	        });*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		String path = url.getPath();
		int indexConf = path.lastIndexOf("/conf");
		String webapp = path.substring(0, indexConf) + "/webapp";
		logger.error(webapp);
		this.webAppPath = webapp;
	}

	public void setTomcatPort(int tomcatPort) {
		this.tomcatPort = tomcatPort;
	}

	public void setContextPath(String contextPath) {
		if (null != contextPath) {
			this.contextPath = contextPath;
		} else {
			this.contextPath = CONTEXT_PATH;
		}
	}

	public void stop() {
		try {
			if (null != tomcat) {
				tomcat.stop();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void destroy() {
		try {
			if (null != tomcat) {
				tomcat.destroy();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public int getTomcatPort() {
		return tomcatPort;
	}

	public String getContextPath() {
		return contextPath;
	}

	public String getWebAppPath() {
		return this.webAppPath;
	}
	
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public static void main(String[] args) {
		try {
			new EmbeddedTomcat().startTomcat();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
