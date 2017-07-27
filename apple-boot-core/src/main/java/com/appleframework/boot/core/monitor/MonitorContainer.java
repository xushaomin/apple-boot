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
import com.appleframework.boot.utils.HttpUtils;
import com.appleframework.boot.utils.NetUtils;
import com.appleframework.boot.utils.SystemPropertiesUtils;
import com.appleframework.config.core.EnvConfigurer;

public class MonitorContainer implements Container {

	private static Logger logger = Logger.getLogger(MonitorContainer.class);
	
	private static long startTime = System.currentTimeMillis();
	
	private static String CONTAINER_NAME = "MonitorContainer";
	
	private static String MONITOR_URL = "http://monitor.appleframework.com/collect/application";

	@Override
	public void start() {
		logger.warn(CONTAINER_NAME + " start");
		startTime = System.currentTimeMillis();
		this.send();
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
		for (int i = 0; i < 2; i++) {
			if(postMessage()) {
				break;
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean postMessage() {
		try {
			logger.warn("发送监控同步数据通知");
			String result = null;
			Properties prop = this.getMonitorProperties();
			Map<String, String> params = new HashMap<String, String>((Map)prop);
			result = HttpUtils.post(MONITOR_URL, params);
			if(null != result)
				return true;
		} catch (Exception e) {
			logger.error("通过httpclient发送监控同步数据通知失败");
		}
		return false;
	}
	
	private Properties getMonitorProperties(){
		Properties prop = SystemPropertiesUtils.getProp();
		String hostName = NetUtils.getLocalHost();
		List<String> runtimeParameters = this.getRuntimeParameters();
		prop.put("node.ip", NetUtils.getIpByHost(hostName));
		prop.put("node.host", hostName);
		prop.put("install.path", getInstallPath());
		prop.put("deploy.env", getDeployEnv());
		prop.put("log.level", Log4jUtils.getRootLoggerLevelString());
		prop.put("java.version", System.getProperty("java.version"));
		prop.put("start.param", runtimeParameters.toString());
		prop.put("mem.max", this.getRuntimeParameter(runtimeParameters, "-Xmx"));
		prop.put("mem.min", this.getRuntimeParameter(runtimeParameters, "-Xms"));
		return prop;
	}
	
	private String getInstallPath() {
		return System.getProperty("user.dir");
	}
	
	private String getDeployEnv() {
		String env = EnvConfigurer.getEnv();
		if (null == env) {
			env = SystemPropertiesUtils.getString(EnvConfigurer.KEY_DEPLOY_ENV);
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
