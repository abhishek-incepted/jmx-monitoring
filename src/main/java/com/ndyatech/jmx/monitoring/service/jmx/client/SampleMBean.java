package com.ndyatech.jmx.monitoring.service.jmx.client;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import java.lang.management.ThreadMXBean;

/**
 * Created by abhishek.joshi on 6/27/2016.
 */
public class SampleMBean {

    private MBeanServerConnection mbsc;

    public MBeanServerConnection getMbsc() {
        return mbsc;
    }

    public void setMbsc(MBeanServerConnection mbsc) {
        this.mbsc = mbsc;
    }

    public void demonstrateClientJmxWithProxy(final ObjectName objectName)
    {
        final ThreadMXBean status = (ThreadMXBean)
                MBeanServerInvocationHandler.newProxyInstance(
                        mbsc,
                        objectName,
                        ThreadMXBean.class,
                        false);
        System.out.println("PROXY (Spring) Says: The Status is " + status.getThreadCount());
    }

}
