package com.ndyatech.jmx.monitoring.service.jmx.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eclectic on 26-06-2016.
 */
public class GCDetails implements JMXDetail {

	private final List<GCDetail> gcDetailList = new LinkedList<GCDetail>();

	public List<GCDetail> getGcDetailList() {
		return gcDetailList;
	}

	public void setGcDetailList(GCDetail gcDetail) {
		this.gcDetailList.add(gcDetail);
	}

	@Override
	public String toString() {
		return "GCDetails{" +
				"gcDetailList=" + gcDetailList +
				'}';
	}
}
