package com.ndyatech.jmx.monitoring.service.jmx.impl;

import com.ndyatech.jmx.monitoring.service.jmx.dto.ThreadDetails;
import com.ndyatech.jmx.monitoring.service.jmx.dto.ThreadMetaInfo;
import com.ndyatech.jmx.monitoring.service.jmx.utils.Utility;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by eclectic on 25-06-2016.
 */
public class ThreadMXBeanService extends AbstractMXBeanService<ThreadDetails> {

	@Override
	protected ThreadDetails execute() {
		ThreadMXBean threadMXBean = Utility.getThreadMXBean();

		final ThreadDetails threadDetails = new ThreadDetails();
		threadDetails.setThreadCount(threadMXBean.getThreadCount());

		long[] threadIds = threadMXBean.getAllThreadIds();

		if(threadIds != null) {
			for (long threadId : threadIds) {
				ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
				if(threadInfo != null) {
					threadDetails.getThreadMetaInfoList().add(new ThreadMetaInfo(threadInfo.getThreadId(), threadInfo
							.getThreadName(), threadInfo.getThreadState()));
				}
			}
		}
		return threadDetails;
	}

}
