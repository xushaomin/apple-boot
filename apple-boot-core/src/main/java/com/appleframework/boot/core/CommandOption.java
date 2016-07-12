package com.appleframework.boot.core;

import org.apache.log4j.Logger;

public class CommandOption {
	
    private static Logger logger = Logger.getLogger(CommandOption.class);

	public static void parser(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String envArgs = args[i];
			String[] envs = envArgs.split("=");
			if(envs.length == 2) {
				System.setProperty(envs[0], envs[1]);
				logger.warn("配置项：" + envs[0] + "=" + envs[1]);
			}
			else {
				logger.error("错误参数：" + envArgs);
			}
		}
	}
}
