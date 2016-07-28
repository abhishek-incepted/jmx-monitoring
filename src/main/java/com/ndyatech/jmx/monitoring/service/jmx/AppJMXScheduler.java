package com.ndyatech.jmx.monitoring.service.jmx;

import com.ndyatech.jmx.monitoring.service.jmx.impl.GCMXBeanService;
import com.ndyatech.jmx.monitoring.service.jmx.impl.MemoryMXBeanService;
import com.ndyatech.jmx.monitoring.service.jmx.impl.ThreadMXBeanService;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by abhishek.joshi on 6/27/2016.
 */
public class AppJMXScheduler {

    private static final AppJMXScheduler INSTANCE = new AppJMXScheduler();
    private static long JMX_SCHEDULE_INTERVAL;

    private final ScheduledExecutorService executorService;

    private AppJMXScheduler() {
        final List<Runnable> jobs = new LinkedList<Runnable>();
        jobs.add(new ThreadMXBeanService());
        jobs.add(new GCMXBeanService());
        //jobs.add(new RuntimeMXBeanService());
        jobs.add(new MemoryMXBeanService());

        // default properties
        JMX_SCHEDULE_INTERVAL = Long.parseLong(System.getProperty("monitoring.jmx.schedule.interval", "1000"));

        final AtomicInteger count = new AtomicInteger(0);
        executorService = Executors.newScheduledThreadPool(jobs.size(), new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName(jobs.get(count.getAndIncrement()).getClass().getName()+"-thread");
                t.setDaemon(false);
                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    public void uncaughtException(Thread t, Throwable e) {
                        System.err.println("Exception occurred in thread[" + t.toString() + ". Reason:" + e);
                    }
                });
                return t;
            }
        });

        // schedule jobs
        for (Runnable job : jobs) {
            executorService.scheduleWithFixedDelay(job, 1L, JMX_SCHEDULE_INTERVAL, TimeUnit.MILLISECONDS);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Shutting down map-reduce monitoring.");
                if(!executorService.isTerminated() || !executorService.isShutdown()) {
                    executorService.shutdown();

                    try {
                        if(executorService.awaitTermination(10000L, TimeUnit.MILLISECONDS)) {
                            if(!executorService.isTerminated()) {
                                executorService.shutdownNow();
                            }
                        }
                    } catch (InterruptedException e) {
                        System.err.println("Exception occurred while stopping executor-service. Reason:"+e);
                    }
                }
            }
        }));
    }

    public static AppJMXScheduler getInstance() {
        return INSTANCE;
    }


}
