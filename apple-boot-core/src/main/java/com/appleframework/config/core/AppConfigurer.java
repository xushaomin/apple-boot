package com.appleframework.config.core;

public class AppConfigurer {
	
	public static String KEY_APPLE_NAME  = "application.name";
	public static String KEY_SPRING_NAME = "spring.application.name";
	
	private static String NAME = null;

	public static String getName() {
		if(null == NAME) {
			NAME = System.getProperty(KEY_APPLE_NAME);
			if(null == NAME) {
				NAME = System.getProperty(KEY_SPRING_NAME);
			}
		}
		return NAME;
	}

	public static void setName(String name) {
		NAME = name;
		System.setProperty(KEY_APPLE_NAME, name);
		System.setProperty(KEY_SPRING_NAME, name);
	}
	
}
