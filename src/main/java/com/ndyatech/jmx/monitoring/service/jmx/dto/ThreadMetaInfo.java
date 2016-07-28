package com.ndyatech.jmx.monitoring.service.jmx.dto;

/**
 * Created by eclectic on 25-06-2016.
 */
public class ThreadMetaInfo implements JMXDetail {

	private final long threadId;
	private final String threadName;
	private final Thread.State threadState;

	public ThreadMetaInfo(long threadId, String threadName, Thread.State threadState) {
		this.threadId = threadId;
		this.threadName = threadName;
		this.threadState = threadState;
	}

	public long getThreadId() {
		return threadId;
	}

	public String getThreadName() {
		return threadName;
	}

	public Thread.State getThreadState() {
		return threadState;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ThreadMetaInfo)) return false;

		ThreadMetaInfo that = (ThreadMetaInfo) o;

		return getThreadId() == that.getThreadId();

	}

	@Override
	public int hashCode() {
		return (int) (getThreadId() ^ (getThreadId() >>> 32));
	}

	@Override
	public String toString() {
		return "ThreadMetaInfo{" +
				"threadId=" + threadId +
				", threadName='" + threadName + '\'' +
				", threadState=" + threadState +
				'}';
	}
}
