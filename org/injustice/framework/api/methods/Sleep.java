/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.framework.api.methods;

import org.injustice.framework.api.Condition;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;
import org.powerbot.script.util.Delay;

/**
 *
 * @author Injustice
 */
public class Sleep extends IMethodProvider {

    public Sleep(final IMethodContext c) {
        super(c);
    }
    
    public void sleep(final long time) {
        Delay.sleep(time);
    }
    
    @Override
    public void sleep(final int minTime, final int maxTime) {
        Delay.sleep(minTime, maxTime);
    }
    
    public void waitFor(final boolean condition, final int attempts, final int sleep) {
        for (int i = 0; i < attempts && condition; i++) {
            sleep(sleep);
        }
    }
    
    public void waitFor(final boolean condition, final int attempts) {
        waitFor(condition, attempts, 600);
    }
    
    public void waitFor(final boolean condition, final long timeout) {
        Condition c = new Condition() {
            @Override
            public boolean activate() {
                return condition;
            }
        };
        while (!c.activate()) {
            sleep(timeout);
        }
    }
    
    public void waitFor(final boolean condition) {
        waitFor(condition, 6000000);
    }

}
