/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.framework.script.job.state;

import org.injustice.framework.api.Condition;
import org.injustice.framework.script.job.Task;

/**
 *
 * @author Injustice
 */
public abstract class InnerNode implements Condition{
    public abstract void execute();
}
