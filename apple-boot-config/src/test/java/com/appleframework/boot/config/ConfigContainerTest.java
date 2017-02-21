package com.appleframework.boot.config;

import com.appleframework.boot.core.Container;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ConfigContainerTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public ConfigContainerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ConfigContainerTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		
		Container container = new ConfigContainer("system.properties");
		container.start();
		
	}
}
