package com.ndyatech.jmx.monitoring.service.jmx.impl;

import com.ndyatech.jmx.monitoring.service.jmx.AppJMXSubscriber;
import com.ndyatech.jmx.monitoring.service.jmx.dto.JMXDetail;

/**
 * Created by abhishek.joshi on 6/27/2016.
 */
public abstract class AbstractMXBeanService<T extends JMXDetail> implements Runnable {

    @Override
    public void run() {

        //System.out.println(this.getClass().getName()+" invoked...");
        try {
            T object = execute();
            if(object != null) {
                AppJMXSubscriber.getInstance().notifyProgress(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected abstract T execute();

}
