package com.ndyatech.jmx.monitoring.service.jmx.dto;

/**
 * Created by eclectic on 26-06-2016.
 */
public class GCDetail {

	private String name;
	private long collectionCount;
	private long collectionTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(long collectionCount) {
		this.collectionCount = collectionCount;
	}

	public long getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(long collectionTime) {
		this.collectionTime = collectionTime;
	}

	@Override
		public String toString() {
			return "GCDetail{" +
					"name='" + name + '\'' +
					", collectionCount=" + collectionCount +
					", collectionTime=" + collectionTime +
					'}';
		}

}
