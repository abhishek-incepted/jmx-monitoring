package com.ndyatech.jmx.monitoring.service.jmx.utils;

import com.ndyatech.jmx.monitoring.service.jmx.client.JMXClient;

import javax.management.MalformedObjectNameException;
import java.io.IOException;
import java.lang.management.*;
import java.util.List;

/**
 * Created by abhishek.joshi on 7/22/2016.
 */
public class Utility {

    public static ThreadMXBean getThreadMXBean() {
        ThreadMXBean threadMXBean = null;
        try {
            threadMXBean = JMXClient.getInstance().getMXBean(ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException("Exception occurred while fetching mBean->ThreadMXBean", e);
        }
        return threadMXBean;
    }

    public static RuntimeMXBean getRuntimeMXBean() {
        RuntimeMXBean runtimeMXBean = null;
        try {
            runtimeMXBean = JMXClient.getInstance().getMXBean(ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException("Exception occurred while fetching mBean->RuntimeMXBean", e);
        }
        return runtimeMXBean;
    }

    public static MemoryMXBean getMemoryMXBean() {
        MemoryMXBean memoryMXBean = null;
        try {
            memoryMXBean = JMXClient.getInstance().getMXBean(ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException("Exception occurred while fetching mBean->RuntimeMXBean", e);
        }
        return memoryMXBean;
    }

    public static List<GarbageCollectorMXBean> getGarbageCollectorMXBean() {
        List<GarbageCollectorMXBean> gcMXBean;
        try {
            gcMXBean = JMXClient.getInstance().getMXBeanCollection(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE+",*", GarbageCollectorMXBean.class);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException("Exception occurred while fetching mBean->GarbageCollectorMXBean", e);
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while fetching mBean->GarbageCollectorMXBean", e);
        }
        return gcMXBean;
    }

}
