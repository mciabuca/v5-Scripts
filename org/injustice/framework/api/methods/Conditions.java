/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import org.injustice.framework.api.Condition;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;
import org.powerbot.script.util.Timer;

/**
 *
 * @author Suhaib
 */
public class Conditions extends IMethodProvider {

    public Conditions(IMethodContext c) {
        super(c);
    }
    public boolean waitFor(long timeout, final boolean bool) {
        Condition condition = new Condition() {
            @Override
            public boolean activate() {
                return bool;
            }
        };
        final Timer timer = new Timer(timeout);
        while (!condition.activate() && timer.isRunning()) {
            this.sleep(500);
        }
        return condition.activate();
    }

    public boolean xor(boolean a, boolean b) {
        return (a && !b) || (!a && b);
    }

    public boolean and(boolean a, boolean b) {
        return a && b;
    }
}
