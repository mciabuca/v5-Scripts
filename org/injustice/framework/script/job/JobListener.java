/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.framework.script.job;

/**
 *
 * @author Injustice
 */
public abstract interface JobListener {
    public abstract void jobStarted(Job job);
 
	public abstract void jobStopped(Job job);
}
