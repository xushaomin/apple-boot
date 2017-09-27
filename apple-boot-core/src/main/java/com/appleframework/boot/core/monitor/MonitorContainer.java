package com.appleframework.boot.core.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.core.log4j.Log4jUtils;
import com.appleframework.boot.utils.ApplicationUtils;
import com.appleframework.boot.utils.HttpUtils;
import com.appleframework.boot.utils.NetUtils;
import com.appleframework.boot.utils.SystemPropertiesUtils;
import com.appleframework.config.core.EnvConfigurer;

public class MonitorContainer implements Container {

	private static Logger logger = Logger.getLogger(MonitorContainer.class);
	
	private static long startTime = System.currentTimeMillis();
	
	private static String CONTAINER_NAME = "MonitorContainer";
	
	@Override
	public void start() {
		logger.warn(CONTAINER_NAME + " start");
		startTime = System.currentTimeMillis();
		this.send();
		
		if(Constants.isMonitorJvm())
			JvmMonitor.getInstance().record();
	}

	@Override
	public void stop() {
		logger.warn(CONTAINER_NAME + " stop");
	}

	@Override
	public void restart() {
		this.send();
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public String getName() {
		return CONTAINER_NAME;
	}

	@Override
	public String getType() {
		return CONTAINER_NAME;
	}
	
	private void send() {
		String applicationUrl = Constants.getApplicationUrl();
		Properties props = getMonitorProperties();
		boolean success = false;
		for (int i = 1; i <= 3; i++) {
			if (postMessage(props, applicationUrl, i)) {
				success = true;
				break;
			}
		}
		if (!success) {
			logger.error("通知监控中心失败，请检查域名" + Constants.getMonitorDomain() + "是否配置有效!");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean postMessage(Properties props, String applicationUrl, int time) {
		try {
			logger.info("第" + time + "次发送监控同步数据通知");
			String result = null;
			Map<String, String> params = new HashMap<String, String>((Map) props);
			result = HttpUtils.post(applicationUrl, params);
			if (null != result)
				return true;
		} catch (Exception e) {
			logger.info("通过httpclient发送监控同步数据通知失败");
		}
		return false;
	}
	
	private Properties getMonitorProperties() {
		String hostName = NetUtils.getLocalHost();
		List<String> runtimeParameters = this.getRuntimeParameters();
		SystemPropertiesUtils.put("application.name", ApplicationUtils.getApplicationName());
		SystemPropertiesUtils.put("node.ip", NetUtils.getIpByHost(hostName));
		SystemPropertiesUtils.put("node.host", hostName);
		SystemPropertiesUtils.put("install.path", getInstallPath());
		SystemPropertiesUtils.put("deploy.env", getDeployEnv());
		SystemPropertiesUtils.put("log.level", Log4jUtils.getRootLoggerLevelString());
		SystemPropertiesUtils.put("java.version", System.getProperty("java.version"));
		SystemPropertiesUtils.put("start.param", runtimeParameters.toString());
		SystemPropertiesUtils.put("mem.max", this.getRuntimeParameter(runtimeParameters, "-Xmx"));
		SystemPropertiesUtils.put("mem.min", this.getRuntimeParameter(runtimeParameters, "-Xms"));
		Integer webPort = SystemPropertiesUtils.getInteger("server.port");
		if(null != webPort) {
			SystemPropertiesUtils.put("web.port", webPort);
		}
		return SystemPropertiesUtils.getProp();
	}
	
	private String getInstallPath() {
		return System.getProperty("user.dir");
	}
	
	private String getDeployEnv() {
		String env = EnvConfigurer.getEnv();
		if (null == env) {
			env = SystemPropertiesUtils.getEnv();
			if (null == env) {
				env = "UNKNOWN";
			}
			else {
				EnvConfigurer.setEnv(env);
			}
		}
		return env;
	}

	public long getStartTime() {
		return startTime;
	}
	
	private List<String> getRuntimeParameters() {
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		List<String> rList = new ArrayList<String>();
		List<String> aList = bean.getInputArguments();
		for (String key : aList) {
			if(key.indexOf("-X") > -1) {
				if(key.indexOf("bootclasspath") == -1) {
					rList.add(key);
				}
			}
		}
		return rList;
	}
	
	//解析启动参数JMX_MEM="-server -Xmx4g -Xms2g -Xmn512m -XX:PermSize=128m -Xss256k"
	private String getRuntimeParameter(List<String> runtimeParameters, String parameter) {
		String value = "UNKNOWN";
		for (String key : runtimeParameters) {
			if(key.indexOf(parameter) > -1) {
				value = key.substring(parameter.length());
			}
		}
		return value;
	}	
}
