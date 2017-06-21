package com.appleframework.config.core;

public class AppConfigurer {
	
	public static String KEY_NAME = "application.name";
	
	private static String NAME = null;

	public static String getName() {
		if(null == NAME) {
			NAME = System.getProperty(KEY_NAME);
		}
		return NAME;
	}

	public static void setName(String name) {
		NAME = name;
		System.setProperty(KEY_NAME, name);
	}
	
}
