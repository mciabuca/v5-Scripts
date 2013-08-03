/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.node.drop;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.Node;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;

/**
 *
 * @author Injustice
 */
public class Mousekeys extends StateNode {
    
    @Override
    public String state() {
        return "Mousekeys";
    }
    private Rock rock;

    public Mousekeys(IMethodContext ctx, Rock rock) {
        super(ctx);
    }

    @Override
    public void execute() {
        ctx.backpack.useMouseKeys();
    }

    @Override
    public boolean activate() {
        return ctx.backpack.isFull() && 
                ctx.backpack.select().id(rock.getInvId()).count() == 28;
    }

}
