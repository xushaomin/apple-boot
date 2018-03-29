package com.appleframework.boot.core;

public class ContainerFactory {

	public static Container create(Class<?> clazz) {
		try {
			return (Container) clazz.newInstance();
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}

}
