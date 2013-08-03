/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api;

import org.powerbot.script.methods.MethodProvider;

/**
 *
 * @author Suhaib
 */
public class IMethodProvider extends MethodProvider {
    public IMethodContext ctx;

    public IMethodProvider(IMethodContext c) {
        super(c);
        this.ctx = c;
    }
}
