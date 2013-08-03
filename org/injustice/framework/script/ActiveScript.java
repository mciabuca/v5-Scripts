/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.framework.script;

import org.injustice.framework.script.job.Container;
import org.injustice.framework.script.job.Job;
import org.injustice.framework.script.job.TaskContainer;
import org.powerbot.script.PollingScript;

/**
 *
 * @author Injustice
 */
public abstract class ActiveScript extends PollingScript {
	private final Container container = new TaskContainer();
 
	protected ActiveScript() {
		getExecQueue(State.SUSPEND).add(new Runnable() {
			@Override
			public void run() {
				setPaused(true);
			}
		});
		getExecQueue(State.RESUME).add(new Runnable() {
			@Override
			public void run() {
				setPaused(false);
			}
		});
		getExecQueue(State.STOP).add(new Runnable() {
			@Override
			public void run() {
				shutdown();
			}
		});
	}
 
	public final boolean isActive() {
		return !container.isTerminated();
	}
 
	public final boolean isPaused() {
		return container.isPaused();
	}
 
	public final void setPaused(boolean paramBoolean) {
		container.setPaused(paramBoolean);
	}
 
	/**
	 * Graceful shutdown
	 */
	public final void shutdown() {
		if (!isShutdown()) {
			container.shutdown();
		}
	}
 
	public final boolean isShutdown() {
		return container.isShutdown();
	}
 
	/**
	 * Force stop
	 */
	public void forceStop() {
		container.interrupt();
	}
 
	public final Container getContainer() {
		return container;
	}
 
	public final void submit(Job job) {
		container.submit(job);
	}
}