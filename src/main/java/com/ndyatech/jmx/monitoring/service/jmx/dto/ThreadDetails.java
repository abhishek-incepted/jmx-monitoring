package com.ndyatech.jmx.monitoring.service.jmx.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eclectic on 25-06-2016.
 */
public class ThreadDetails implements JMXDetail {

	private int threadCount;
	private final List<ThreadMetaInfo> threadMetaInfoList = new LinkedList<ThreadMetaInfo>();

	public List<ThreadMetaInfo> getThreadMetaInfoList() {
		return threadMetaInfoList;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	@Override
	public String toString() {
		return "ThreadDetails{" +
				"threadCount=" + threadCount +
				", threadMetaInfoList=" + threadMetaInfoList +
				'}';
	}
}
