package com.appleframework.boot.jetty.spring;

public class ConnectorAttribute {

	private Integer port;

	private Integer acceptQueueSize; // 每个请求被accept前允许等待的连接数

	private Integer acceptors; // 同事监听read事件的线程数

	private Integer selectors;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getAcceptQueueSize() {
		return acceptQueueSize;
	}

	public void setAcceptQueueSize(Integer acceptQueueSize) {
		this.acceptQueueSize = acceptQueueSize;
	}

	public Integer getAcceptors() {
		return acceptors;
	}

	public void setAcceptors(Integer acceptors) {
		this.acceptors = acceptors;
	}

	public Integer getSelectors() {
		return selectors;
	}

	public void setSelectors(Integer selectors) {
		this.selectors = selectors;
	}

}