/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.framework.script.job;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;

/**
 *
 * @author Injustice
 */
public abstract class Job extends IMethodProvider {
    public Job(IMethodContext ctx) {
		super(ctx);
	}
 
	public abstract void work();
 
	public abstract boolean join();
 
	public abstract boolean isAlive();
 
	public abstract void interrupt();
 
	public abstract boolean isInterrupted();
 
	public abstract void setContainer(Container container);
 
	public abstract Container getContainer();
}
