package com.appleframework.boot.core;

import java.util.Enumeration;

public interface Attributes {
	
	public void removeAttribute(String name);

	public void setAttribute(String name, Object attribute);

	public Object getAttribute(String name);

	public Enumeration<String> getAttributeNames();

	public void clearAttributes();
}
