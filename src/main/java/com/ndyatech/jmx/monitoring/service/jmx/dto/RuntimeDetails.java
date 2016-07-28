package com.ndyatech.jmx.monitoring.service.jmx.dto;

import java.util.Map;

/**
 * Created by eclectic on 26-06-2016.
 */
public class RuntimeDetails implements JMXDetail {

	private String classPath;
	private String name;
	private long startTime;
	private long upTime;
	private String vmTime;
	private String vmVersion;
	private Map<String, String> systemProperties;

	public Map<String, String> getSystemProperties() {
		return systemProperties;
	}

	public void setSystemProperties(Map<String, String> systemProperties) {
		this.systemProperties = systemProperties;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getUpTime() {
		return upTime;
	}

	public void setUpTime(long upTime) {
		this.upTime = upTime;
	}

	public String getVmTime() {
		return vmTime;
	}

	public void setVmTime(String vmTime) {
		this.vmTime = vmTime;
	}

	public String getVmVersion() {
		return vmVersion;
	}

	public void setVmVersion(String vmVersion) {
		this.vmVersion = vmVersion;
	}

	@Override
	public String toString() {
		return "RuntimeDetails{" +
				"classPath='" + classPath + '\'' +
				", name='" + name + '\'' +
				", startTime=" + startTime +
				", upTime=" + upTime +
				", vmTime='" + vmTime + '\'' +
				", vmVersion='" + vmVersion + '\'' +
				", systemProperties=" + systemProperties +
				'}';
	}
}
