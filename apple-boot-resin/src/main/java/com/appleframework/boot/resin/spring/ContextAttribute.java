package com.appleframework.boot.resin.spring;

public class ContextAttribute {

	private Integer port;

	private Integer executorTaskMax;
	private Integer idleMax;
	private Integer idleMin;
	private Long idleTimeout;
	private Integer priorityIdleMin;
	private Integer threadMax;
	private Integer throttleLimit;
	private Integer throttlePeriod;
	private Long throttleSleepTime;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getExecutorTaskMax() {
		return executorTaskMax;
	}

	public void setExecutorTaskMax(Integer executorTaskMax) {
		this.executorTaskMax = executorTaskMax;
	}

	public Integer getIdleMax() {
		return idleMax;
	}

	public void setIdleMax(Integer idleMax) {
		this.idleMax = idleMax;
	}

	public Integer getIdleMin() {
		return idleMin;
	}

	public void setIdleMin(Integer idleMin) {
		this.idleMin = idleMin;
	}

	public Long getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(Long idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public Integer getPriorityIdleMin() {
		return priorityIdleMin;
	}

	public void setPriorityIdleMin(Integer priorityIdleMin) {
		this.priorityIdleMin = priorityIdleMin;
	}

	public Integer getThreadMax() {
		return threadMax;
	}

	public void setThreadMax(Integer threadMax) {
		this.threadMax = threadMax;
	}

	public Integer getThrottleLimit() {
		return throttleLimit;
	}

	public void setThrottleLimit(Integer throttleLimit) {
		this.throttleLimit = throttleLimit;
	}

	public Integer getThrottlePeriod() {
		return throttlePeriod;
	}

	public void setThrottlePeriod(Integer throttlePeriod) {
		this.throttlePeriod = throttlePeriod;
	}

	public Long getThrottleSleepTime() {
		return throttleSleepTime;
	}

	public void setThrottleSleepTime(Long throttleSleepTime) {
		this.throttleSleepTime = throttleSleepTime;
	}

}