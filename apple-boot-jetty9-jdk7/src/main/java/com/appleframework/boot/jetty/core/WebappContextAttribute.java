package com.appleframework.boot.jetty.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.boot.core.Attributes;
import com.appleframework.boot.utils.ResourceUtils;

/**
 * @author xusm(2012-11-22)
 *
 */
public class WebappContextAttribute implements Attributes {

	private static Logger logger = LoggerFactory.getLogger(WebappContextAttribute.class);

	private static final String PROPERTIES_FILE_NAME = "jetty.properties";

	private static Properties prop = null;

	static {
		try {
			load(ResourceUtils.getAsStream(PROPERTIES_FILE_NAME));
		} catch (Exception e) {
			logger.error("error happen when loading jetty properties file");
		}
	}

	private static void load(InputStream is) {
		prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			logger.error("error happen when loading jetty properties file");
		}
	}

	public static Properties getProp() {
		return prop;
	}

	@SuppressWarnings("rawtypes")
	public static Iterator getIterator() {
		return prop.entrySet().iterator();
	}

	@Override
	public void removeAttribute(String name) {
		prop.remove(name);
	}

	@Override
	public void setAttribute(String name, Object attribute) {
		prop.setProperty(name, attribute.toString());
	}

	@Override
	public Object getAttribute(String name) {
		if (null == prop) {
			return null;
		}
		else {
			return prop.get(name);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration<String> getAttributeNames() {
		return (Enumeration<String>) prop.propertyNames();
	}

	@Override
	public void clearAttributes() {
		prop.clear();
	}

}
