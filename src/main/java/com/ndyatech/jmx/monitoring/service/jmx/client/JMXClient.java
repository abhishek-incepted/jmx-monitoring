package com.ndyatech.jmx.monitoring.service.jmx.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.lang.management.*;
import java.util.*;
import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Created by abhishek.joshi on 6/27/2016.
 */
public class JMXClient {

    private static String HOST_NAME = System.getProperty("monitoring.jmx.server.host", "localhost");
    private static long HOST_PORT = Long.parseLong(System.getProperty("monitoring.jmx.server.port", "50420"));

    private MBeanServerConnection mbsc;
    private JMXConnector jmxc;
    private static final JMXClient INSTANCE = new JMXClient();

    private JMXClient() {
        try {
            // Create an RMI connector client and
            // connect it to the RMI connector server
            //
            echo("\nCreate an RMI connector client and " +
                    "connect it to the RMI connector server");
            JMXServiceURL url =
                    new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+HOST_NAME+":"+HOST_PORT+"/jmxrmi");
            this.jmxc = JMXConnectorFactory.connect(url, null);

            // Create listener
            //
            ClientListener listener = new ClientListener();

            // Get an MBeanServerConnection
            //
            echo("\nGet an MBeanServerConnection");
            this.mbsc = jmxc.getMBeanServerConnection();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while creating jmx-client! Reason: ", e);
        }
    }

    public static JMXClient getInstance() {
        return INSTANCE;
    }

    public void close() {
        // Close MBeanServer connection
        //
        echo("\nClose the connection to the server");
        try {
            this.jmxc.close();
        } catch (IOException e) {
            System.out.println("Excpetion occurred while closing jmx-client! Reason: " + e);
        }
    }

    public <T> List<T> getMXBeanCollection(String objectName, Class<T> clazz) throws MalformedObjectNameException, IOException {
        List<T> garbageCollectorMXBeanList = new LinkedList<T>();
        // Construct the ObjectName for the Hello MBean
        ObjectName mbeanName = new ObjectName(objectName);
        for(ObjectName name : this.mbsc.queryNames(mbeanName, null)) {
            garbageCollectorMXBeanList.add(getMXBean(name, clazz));
        }
        return garbageCollectorMXBeanList;
    }

    public <T> T getMXBean(String objectName, Class<T> clazz) throws MalformedObjectNameException {
        // Construct the ObjectName for the Hello MBean
        ObjectName mbeanName = new ObjectName(objectName);

        // Create a dedicated proxy for the MBean instead of
        // going directly through the MBean server connection
        return JMX.newMXBeanProxy(this.mbsc, mbeanName, clazz, true);
    }

    public <T> T getMXBean(ObjectName objectName, Class<T> clazz) throws MalformedObjectNameException {
        // Create a dedicated proxy for the MBean instead of
        // going directly through the MBean server connection
        return JMX.newMXBeanProxy(this.mbsc, objectName, clazz, true);
    }

    public static void main(String[] args) throws Exception {
        startJMXClientWithoutSpring();
        //startJMXClientWithSpring();
    }

    private static void startJMXClientWithSpring() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"context/jmx-context.xml"});

        try {
            ctx.getBean(SampleMBean.class).demonstrateClientJmxWithProxy(new ObjectName("java.lang:type=Threading"));
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }

    }

    private static void startJMXClientWithoutSpring() {
        JMXConnector jmxc = null;
        try {
            // Create an RMI connector client and
            // connect it to the RMI connector server
            //
            echo("\nCreate an RMI connector client and " +
                    "connect it to the RMI connector server");
            JMXServiceURL url =
                    new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+HOST_NAME+":"+HOST_PORT+"/jmxrmi");
            jmxc = JMXConnectorFactory.connect(url, null);

            // Create listener
            //
            ClientListener listener = new ClientListener();

            // Get an MBeanServerConnection
            //
            echo("\nGet an MBeanServerConnection");
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
            waitForEnterPressed();

            // Get domains from MBeanServer
            //
            echo("\nDomains:");
            String domains[] = mbsc.getDomains();
            Arrays.sort(domains);
            for (String domain : domains) {
                echo("\tDomain = " + domain);
            }
            waitForEnterPressed();

            // Get MBeanServer's default domain
            //
            echo("\nMBeanServer default domain = " + mbsc.getDefaultDomain());

            // Get MBean count
            //
            echo("\nMBean count = " + mbsc.getMBeanCount());

            // Query MBean names
            //
            echo("\nQuery MBeanServer MBeans:");
            Set<ObjectName> names =
                    new TreeSet<ObjectName>(mbsc.queryNames(null, null));
            for (ObjectName name : names) {
                echo("\tObjectName = " + name);
            }
            waitForEnterPressed();

            // ----------------------
            // Manage the Hello MBean
            // ----------------------

            echo("\n>>> Perform operations on Hello MBean <<<");

            // Construct the ObjectName for the Hello MBean
            //
            ObjectName mbeanName1 = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME+",*");
            for(ObjectName name : mbsc.queryNames(mbeanName1, null)) {
                ThreadMXBean threadMXBean = JMX.newMXBeanProxy(mbsc, name, ThreadMXBean.class, false);
                System.out.println("mBean -> "+threadMXBean);
                long[] threadIds = threadMXBean.getAllThreadIds();

                if(threadIds != null) {
                    for (long threadId : threadIds) {
                        long[] l = new long[1];
                        l[0] = threadId;
                        ThreadInfo[] threadInfo = threadMXBean.getThreadInfo(l, true, true);
                        if(threadInfo != null && threadInfo.length > 0) {
                            StackTraceElement[] stackTraceElements = threadInfo[0].getStackTrace();
                            StringBuilder stack = new StringBuilder();
                            for (StackTraceElement se : stackTraceElements) {
                                stack.append(se.toString());
                            }
                            System.out.println("stack -> \n"+stack);
                        }
                    }
                }
            }


            /*ObjectName mbeanName = new ObjectName(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE+",*");//,name=PS Scavenge

            // Create a dedicated proxy for the MBean instead of
            // going directly through the MBean server connection
            //

            for(ObjectName name : mbsc.queryNames(mbeanName, null)) {
                GarbageCollectorMXBean mBean = JMX.newMXBeanProxy(mbsc, name, GarbageCollectorMXBean.class, false);
                System.out.println("mBean -> "+mBean);
            }

            Object o = JMX.newMXBeanProxy(mbsc, mbeanName, GarbageCollectorMXBean.class, false);
            ThreadMXBean threadMXBean = JMX.newMXBeanProxy(mbsc, mbeanName, ThreadMXBean.class, false);
            System.out.println("Thread count -> "+threadMXBean.getThreadCount());

            long[] threadIds = threadMXBean.getAllThreadIds();

            if(threadIds != null) {
                for (long threadId : threadIds) {
                    //Object o = threadMXBean.getThreadInfo(threadId);
                    ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
                    if(threadInfo != null) {
                        System.out.println(new ThreadMetaInfo(threadInfo.getThreadId(), threadInfo
                                .getThreadName(), threadInfo.getThreadState()));
                    }
                }
            }*/

            waitForEnterPressed();
        /*HelloMBean mbeanProxy =
                JMX.newMBeanProxy(mbsc, mbeanName, HelloMBean.class, true);

        // Add notification listener on Hello MBean
        //
        echo("\nAdd notification listener...");
        mbsc.addNotificationListener(mbeanName, listener, null, null);

        // Get CacheSize attribute in Hello MBean
        //
        echo("\nCacheSize = " + mbeanProxy.getCacheSize());

        // Set CacheSize attribute in Hello MBean
        // Calling "reset" makes the Hello MBean emit a
        // notification that will be received by the registered
        // ClientListener.
        //
        mbeanProxy.setCacheSize(150);

        // Sleep for 2 seconds to have time to receive the notification
        //
        echo("\nWaiting for notification...");
        sleep(2000);

        // Get CacheSize attribute in Hello MBean
        //
        echo("\nCacheSize = " + mbeanProxy.getCacheSize());

        // Invoke "sayHello" in Hello MBean
        //
        echo("\nInvoke sayHello() in Hello MBean...");
        mbeanProxy.sayHello();

        // Invoke "add" in Hello MBean
        //
        echo("\nInvoke add(2, 3) in Hello MBean...");
        echo("\nadd(2, 3) = " + mbeanProxy.add(2, 3));

        waitForEnterPressed();

        // ------------------------------
        // Manage the QueueSampler MXBean
        // ------------------------------

        echo("\n>>> Perform operations on QueueSampler MXBean <<<");

        // Construct the ObjectName for the QueueSampler MXBean
        //
        ObjectName mxbeanName =
                new ObjectName("com.example:type=QueueSampler");

        // Create a dedicated proxy for the MXBean instead of
        // going directly through the MBean server connection
        //
        QueueSamplerMXBean mxbeanProxy =
                JMX.newMXBeanProxy(mbsc, mxbeanName, QueueSamplerMXBean.class);

        // Get QueueSample attribute in QueueSampler MXBean
        //
        QueueSample queue1 = mxbeanProxy.getQueueSample();
        echo("\nQueueSample.Date = " + queue1.getDate());
        echo("QueueSample.Head = " + queue1.getHead());
        echo("QueueSample.Size = " + queue1.getSize());

        // Invoke "clearQueue" in QueueSampler MXBean
        //
        echo("\nInvoke clearQueue() in QueueSampler MXBean...");
        mxbeanProxy.clearQueue();

        // Get QueueSample attribute in QueueSampler MXBean
        //
        QueueSample queue2 = mxbeanProxy.getQueueSample();
        echo("\nQueueSample.Date = " + queue2.getDate());
        echo("QueueSample.Head = " + queue2.getHead());
        echo("QueueSample.Size = " + queue2.getSize());

        waitForEnterPressed();*/

        } catch (Exception e) {
            System.err.println("Exception occurred while running JMX client. Reason: " + e);
            e.printStackTrace();
        } finally {
            // Close MBeanServer connection
            //
            echo("\nClose the connection to the server");
            try {
                if(jmxc != null)
                    jmxc .close();
            } catch (IOException e) {
                echo("\nCould not close jmx-client. Reason: "+e);
            }
            echo("\nBye! Bye!");
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void waitForEnterPressed() {
        try {
            echo("\nPress <Enter> to continue...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inner class that will handle the notifications.
     */
    public static class ClientListener implements NotificationListener {
        public void handleNotification(Notification notification,
                                       Object handback) {
            echo("\nReceived notification:");
            echo("\tClassName: " + notification.getClass().getName());
            echo("\tSource: " + notification.getSource());
            echo("\tType: " + notification.getType());
            echo("\tMessage: " + notification.getMessage());
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn =
                        (AttributeChangeNotification) notification;
                echo("\tAttributeName: " + acn.getAttributeName());
                echo("\tAttributeType: " + acn.getAttributeType());
                echo("\tNewValue: " + acn.getNewValue());
                echo("\tOldValue: " + acn.getOldValue());
            }
        }
    }

}
