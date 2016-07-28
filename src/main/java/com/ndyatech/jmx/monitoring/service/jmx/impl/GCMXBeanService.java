package com.ndyatech.jmx.monitoring.service.jmx.impl;

import com.ndyatech.jmx.monitoring.service.jmx.dto.GCDetail;
import com.ndyatech.jmx.monitoring.service.jmx.dto.GCDetails;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

import static com.ndyatech.jmx.monitoring.service.jmx.utils.Utility.getGarbageCollectorMXBean;

/**
 * Created by eclectic on 26-06-2016.
 */
public class GCMXBeanService extends AbstractMXBeanService<GCDetails> {

	@Override
	protected GCDetails execute() {
		List<GarbageCollectorMXBean> garbageCollectorMXBeanList = getGarbageCollectorMXBean();

		GCDetails gcDetails = null;
		if(garbageCollectorMXBeanList != null) {
			for (GarbageCollectorMXBean gcMXBean : garbageCollectorMXBeanList) {
				if(gcDetails == null) {
					gcDetails = new GCDetails();
				}
				GCDetail gcDetail = new GCDetail();
				gcDetail.setName(gcMXBean.getName());
				gcDetail.setCollectionCount(gcMXBean.getCollectionCount());
				gcDetail.setCollectionTime(gcMXBean.getCollectionTime());
				gcDetails.setGcDetailList(gcDetail);
			}
		}
		return gcDetails;
	}

}
