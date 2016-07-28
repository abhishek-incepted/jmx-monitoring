package com.ndyatech.jmx.monitoring.service.jmx;

import com.ndyatech.jmx.monitoring.service.jmx.dto.JMXDetail;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by abhishek.joshi on 6/27/2016.
 */
public class AppJMXSubscriber {

    private static CopyOnWriteArrayList<SseEmitter> subcribers = new CopyOnWriteArrayList<SseEmitter>();

    private static final AppJMXSubscriber INSTANCE = new AppJMXSubscriber();

    private AppJMXSubscriber() {}

    public static AppJMXSubscriber getInstance() {
        return INSTANCE;
    }

    public void subscribe(final SseEmitter sseEmitter) {
        subcribers.add(sseEmitter);
        System.out.println("Subscriber added.");
    }

    public void unsubscribe(final SseEmitter sseEmitter) {
        subcribers.remove(sseEmitter);
        System.out.println("Subscriber removed.");
    }

    public  <T extends JMXDetail> void notifyProgress(T details) {
        //System.out.println("notifyProgress -> subcribers.size()="+subcribers.size());
        for (SseEmitter eachSseEmitter : subcribers) {  // TODO: what if any removed...
            try {
                eachSseEmitter.send(details);
                //System.out.println("notifyProgress done -> "+details);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
