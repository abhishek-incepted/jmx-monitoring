package com.ndyatech.jmx.monitoring.rest.services.controllers;

import com.ndyatech.jmx.monitoring.service.jmx.AppJMXSubscriber;
import com.ndyatech.jmx.monitoring.service.jmx.utils.Utility;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by eclectic on 25-06-2016.
 */
@RestController
@RequestMapping(value = "/jmx")
public class JMXMonitoringController {

	@RequestMapping(value = "/stream/mapReduce", method = RequestMethod.GET)
	public @ResponseBody SseEmitter getMapReduceJmxMonitoring() {
		System.out.println("/jmx/mapReduce invoked...");
		final SseEmitter sseEmitter = new SseEmitter(7*84000000L);

		sseEmitter.onCompletion(new Runnable() {
			@Override
			public void run() {
				System.out.println("Client disconnected!");
				AppJMXSubscriber.getInstance().unsubscribe(sseEmitter);
			}
		});

		sseEmitter.onTimeout(new Runnable() {
			@Override
			public void run() {
				System.out.println("Client timed-out!");
				AppJMXSubscriber.getInstance().unsubscribe(sseEmitter);
			}
		});

		AppJMXSubscriber.getInstance().subscribe(sseEmitter);

		return sseEmitter;
	}

	@RequestMapping(value = "/thread/{threadId}", method = RequestMethod.GET)
	public @ResponseBody String getThreadStack(@PathVariable("threadId") String threadId) {
		ThreadMXBean threadMXBean = Utility.getThreadMXBean();
		long[] tId = new long[]{Long.parseLong(threadId)};
		ThreadInfo[] tInfo = threadMXBean.getThreadInfo(tId, true, true);
		StringBuilder stack = new StringBuilder();
		if(tInfo != null && tInfo.length > 0) {
			StackTraceElement[] stackTraceElements = tInfo[0].getStackTrace();
			for (StackTraceElement se : stackTraceElements) {
				stack.append(se.toString());
			}
		}
		return stack.toString();
	}

}
