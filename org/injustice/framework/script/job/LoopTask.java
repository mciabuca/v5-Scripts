/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.framework.script.job;

import org.injustice.framework.api.IMethodContext;

/**
 *
 * @author Injustice
 */
public abstract class LoopTask extends Task {
    private boolean paused;
 
	public LoopTask(IMethodContext ctx) {
		super(ctx);
	}
 
	public final void execute() {
		Container container = getContainer();
		while (!container.isShutdown()) {
			if (container.isPaused()) {
				paused = true;
				sleep(1000);
			} else {
				paused = false;
 
				int delay;
				try {
					delay = loop();
				} catch (Throwable throwable) {
					throwable.printStackTrace();
					delay = -1;
				}
 
				if (delay >= 0) {
					sleep(delay);
				} else if (delay == -1) {
					break;
				}
			}
		}
	}
 
	public abstract int loop();
 
	public boolean isPaused() {
		return paused;
	}
}
