package com.appleframework.boot.core.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.appleframework.boot.utils.HttpUtils;
import com.appleframework.boot.utils.SystemPropertiesUtils;
import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class JvmMonitor {
	
    private static Logger logger = Logger.getLogger(JvmMonitor.class);
    
    public static final int DEFAULT_REFRESH_SECONDS = 60;
	
	private static JvmMonitor instance = null;
	
	public static boolean isRecord = true;
	
    private long lastProcessCpuTime = 0;
    private long lastUptime = 0;

	public synchronized static JvmMonitor getInstance(int periodSeconds) {
		if (instance == null)
			instance = new JvmMonitor(periodSeconds);
		return instance;
	}

	public synchronized static JvmMonitor getInstance() {
		if (instance == null)
			instance = new JvmMonitor();
		return instance;
	}
    
    private JvmMonitor() {
        this(DEFAULT_REFRESH_SECONDS);
    }

	private JvmMonitor(int periodSeconds) {
		logger.info("jvm monitor start  ...");
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if(isRecord)
					record();
			}
		}, periodSeconds, periodSeconds, TimeUnit.SECONDS);
	}

    public void record() {
		String message = "memoryUsed=" + getMemoryUsed() + "k " + 
				"cpuUsed=" + getCpu() + " threadCount=" + getThreadCount();
		logger.info(message);

		Map<String, String> params = new HashMap<String, String>();
		params.put("jvm.memoryUsed", getMemoryUsed() + "k ");
		params.put("jvm.cpuUsed", getCpu() + "");
		params.put("jvm.threadCount", getThreadCount() + "");
		
		params.put("application.name", SystemPropertiesUtils.getApplicationName());
		params.put("node.ip", SystemPropertiesUtils.getString("node.ip"));
		params.put("node.host", SystemPropertiesUtils.getString("node.host"));
		
		try {
			HttpUtils.post(Constants.getMonitorUrl(), params);
		} catch (Exception e) {
			logger.info("Post monitor info error : " + e.getMessage());
		}
    }

    protected int getThreadCount() {
        return ManagementFactory.getThreadMXBean().getThreadCount();
    }

    protected long getMemoryUsed() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024);
    }

    protected double getCpu() {
        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runbean = java.lang.management.ManagementFactory.getRuntimeMXBean();
        long uptime = runbean.getUptime();
        long processCpuTime = osbean.getProcessCpuTime();
        //cpu count
        int processors = osbean.getAvailableProcessors();
        //uptime in milliseconds ,and processCpuTime in nao seconds
        double cpu = (processCpuTime - lastProcessCpuTime) / ((uptime - lastUptime) * 10000f * processors);
        lastProcessCpuTime = processCpuTime;
        lastUptime = uptime;
        return (int) cpu;  //
    }   

}