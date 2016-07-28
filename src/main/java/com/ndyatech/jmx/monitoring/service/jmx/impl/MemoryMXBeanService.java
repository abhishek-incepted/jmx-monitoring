package com.ndyatech.jmx.monitoring.service.jmx.impl;

import com.ndyatech.jmx.monitoring.service.jmx.dto.MemoryMXBeanDetails;

import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import static com.ndyatech.jmx.monitoring.service.jmx.utils.Utility.*;

/**
 * Created by abhishek.joshi on 6/27/2016.
 */
public class MemoryMXBeanService extends AbstractMXBeanService<MemoryMXBeanDetails> {

    @Override
    protected MemoryMXBeanDetails execute() {
        MemoryMXBean memoryMXBean = getMemoryMXBean();

        MemoryMXBeanDetails memoryMXBeanDetails = new MemoryMXBeanDetails();

        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        memoryMXBeanDetails.setUsedMemory(memoryUsage.getUsed());
        memoryMXBeanDetails.setMaxMemory(memoryUsage.getMax());
        memoryMXBeanDetails.setUsedNonHeapMemory(nonHeapMemoryUsage.getUsed());
        memoryMXBeanDetails.setMaxNonHeapMemory(nonHeapMemoryUsage.getMax());

        return memoryMXBeanDetails;
    }

}
