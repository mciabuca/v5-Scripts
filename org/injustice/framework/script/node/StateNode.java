/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.script.node;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.Node;

/**
 *
 * @author Injustice
 */
public abstract class StateNode extends Node implements Stateable {
    public StateNode(IMethodContext ctx) {
        super(ctx);
    }
}
