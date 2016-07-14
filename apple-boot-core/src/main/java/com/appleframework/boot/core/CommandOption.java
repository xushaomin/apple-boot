package com.appleframework.boot.core;

import org.apache.log4j.Logger;

import com.appleframework.config.core.EnvConfigurer;

public class CommandOption {

	private static Logger logger = Logger.getLogger(CommandOption.class);

	public static void parser(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String envArgs = args[i];
			String[] envs = envArgs.split("=");
			if (envs.length == 2) {
				String key = envs[0];
				String value = envs[1];
				System.setProperty(key, value);
				logger.warn("配置项：" + key + "=" + value);
				if(key.trim().toLowerCase().indexOf("env") > -1) {
					EnvConfigurer.env = value;
				}
			} else {
				logger.error("错误参数：" + envArgs);
			}
		}
	}

}
