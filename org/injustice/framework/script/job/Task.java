/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.framework.script.job;

import java.util.concurrent.Future;
import java.util.logging.Logger;
import org.injustice.framework.api.IMethodContext;

/**
 *
 * @author Injustice
 */
public abstract class Task extends Job {
    private final Object init_lock = new Object();
	protected Logger log = Logger.getLogger(getClass().getName());
	Future<?> future;
	private Thread thread;
	private Container container = null;
	private volatile boolean alive = false;
	private volatile boolean interrupted = false;
 
	protected Task(IMethodContext ctx) {
		super(ctx);
	}
 
	public final void work() {
		synchronized (init_lock) {
			if (alive) {
				throw new RuntimeException("Task RuntimeException");
			}
			alive = true;
		}
		interrupted = false;
		thread = Thread.currentThread();
		try {
			execute();
		} catch (ThreadDeath ignored) {
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		alive = false;
	}
 
	public abstract void execute();
 
	public final boolean join() {
		if (future == null || future.isDone()) {
			return true;
		}
		try {
			future.get();
		} catch (Throwable ignored) {
		}
		return future.isDone();
	}
 
	public final boolean isAlive() {
		return alive;
	}
 
	public final void interrupt() {
		interrupted = true;
		if (thread != null) {
			try {
				if (!thread.isInterrupted()) {
					thread.interrupt();
				}
			} catch (Throwable ignored) {
			}
		}
	}
 
	public final boolean isInterrupted() {
		return interrupted;
	}
 
	public Container getContainer() {
		return container;
	}
 
	public void setContainer(Container container) {
		this.container = container;
	}
}
