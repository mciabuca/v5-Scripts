/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.framework.script.job.util;

import org.injustice.framework.script.job.Container;
import org.injustice.framework.script.job.Job;
import org.injustice.framework.script.job.JobListener;

/**
 *
 * @author Injustice
 */
public class Containers {
    public static boolean awaitTermination(Container container) {
		return awaitTermination(container, 0);
	}
 
	public static boolean awaitTermination(Container container, int timeout) {
		if (container.isTerminated()) {
			return true;
		}
		Object lock = new Object();
		JobListener jobListener = new JobListener() {
			public void jobStarted(Job paramAnonymousJob) {
			}
 
			public void jobStopped(Job paramAnonymousJob) {
				synchronized (this) {
					notify();
				}
				return;
			}
		};
		container.addListener(jobListener);
		long end = System.currentTimeMillis() + timeout;
		while (((timeout == 0) || (System.currentTimeMillis() < end)) && (!container.isTerminated())) {
			long timeLeft = end - System.currentTimeMillis();
			if (timeLeft <= 0) {
				break;
			}
			synchronized (lock) {
				try {
					lock.wait(timeLeft);
				} catch (InterruptedException localInterruptedException) {
					container.removeListener(jobListener);
					throw new ThreadDeath();
				}
			}
		}
		container.removeListener(jobListener);
		return container.isTerminated();
	}
}
