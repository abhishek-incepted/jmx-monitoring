package com.ndyatech.jmx.monitoring.service.jmx.dto;

/**
 * Created by abhishek.joshi on 6/27/2016.
 */
public class MemoryMXBeanDetails implements JMXDetail {

    private long usedMemory;
    private long maxMemory;
    private long usedNonHeapMemory;
    private long maxNonHeapMemory;

    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public long getUsedNonHeapMemory() {
        return usedNonHeapMemory;
    }

    public void setUsedNonHeapMemory(long usedNonHeapMemory) {
        this.usedNonHeapMemory = usedNonHeapMemory;
    }

    public long getMaxNonHeapMemory() {
        return maxNonHeapMemory;
    }

    public void setMaxNonHeapMemory(long maxNonHeapMemory) {
        this.maxNonHeapMemory = maxNonHeapMemory;
    }

    @Override
    public String toString() {
        return "MemoryMXBeanDetails{" +
                "usedMemory=" + usedMemory +
                ", maxMemory=" + maxMemory +
                ", usedNonHeapMemory=" + usedNonHeapMemory +
                ", maxNonHeapMemory=" + maxNonHeapMemory +
                '}';
    }
}
