package com.appleframework.boot.core;

import org.apache.log4j.Logger;

import com.appleframework.config.core.AppConfigurer;
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
				logger.warn("配置项：" + key + "=" + value);
				if (key.trim().toLowerCase().indexOf("env") > -1) {
					EnvConfigurer.setEnv(value);
				} else if (key.trim().toLowerCase().indexOf("name") > -1) {
					AppConfigurer.setName(value);
				} else {
					System.setProperty(key, value);
				}
			} else {
				logger.error("错误参数：" + envArgs);
			}
		}
	}

}
