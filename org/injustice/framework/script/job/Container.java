/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.framework.script.job;

/**
 *
 * @author Injustice
 */
public abstract interface Container {
    public abstract void submit(Job job);
 
	public abstract void setPaused(boolean paused);
 
	public abstract boolean isPaused();
 
	public abstract Job[] enumerate();
 
	public abstract int getActiveCount();
 
	public abstract Container branch();
 
	public abstract Container[] getChildren();
 
	public abstract void shutdown();
 
	public abstract boolean isShutdown();
 
	public abstract void interrupt();
 
	public abstract boolean isTerminated();
 
	public abstract void addListener(JobListener jobListener);
 
	public abstract void removeListener(JobListener jobListener);
}