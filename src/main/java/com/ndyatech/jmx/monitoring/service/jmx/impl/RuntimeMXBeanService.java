package com.ndyatech.jmx.monitoring.service.jmx.impl;

import com.ndyatech.jmx.monitoring.service.jmx.dto.RuntimeDetails;

import java.lang.management.RuntimeMXBean;

import static com.ndyatech.jmx.monitoring.service.jmx.utils.Utility.*;

/**
 * Created by eclectic on 26-06-2016.
 */
public class RuntimeMXBeanService extends AbstractMXBeanService<RuntimeDetails> {

	@Override
	public RuntimeDetails execute() {
		RuntimeMXBean runtimeMXBean = getRuntimeMXBean();

		final RuntimeDetails runtimeDetails = new RuntimeDetails();
		runtimeDetails.setClassPath(runtimeMXBean.getClassPath());
		runtimeDetails.setName(runtimeMXBean.getName());
		runtimeDetails.setStartTime(runtimeMXBean.getStartTime());
		runtimeDetails.setUpTime(runtimeMXBean.getUptime());
		runtimeDetails.setVmTime(runtimeMXBean.getVmName());
		runtimeDetails.setVmVersion(runtimeMXBean.getVmVersion());
		runtimeDetails.setSystemProperties(runtimeMXBean.getSystemProperties());

		return runtimeDetails;
	}

}
