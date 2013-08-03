/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.node;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.Branch;
import org.injustice.framework.script.job.state.Node;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;

/**
 *
 * @author Injustice
 */
public class DropBranch extends Branch {
    
    private StateNode[] nodes;
    private Rock rock;
    private final int[] priorityItems =  {
        1621, 1617, 1619, 1623, 1625, 
        1627, 1629, 1631, 21345, 6979, 6981, 6983 
    };

    public DropBranch(IMethodContext ctx, StateNode[] nodes, Rock rock) {
        super(ctx, nodes);
        this.rock = rock;
        this.nodes = nodes;
    }

    @Override
    public boolean branch() {
        return ctx.backpack.containsAnyOf(priorityItems) ||
                (ctx.backpack.isFull() && !ctx.backpack.select().id(rock.getInvId()).isEmpty());
    }

    @Override
    public String state() {
        for (StateNode n : nodes) {
            if (n.isAlive()) {
                return n.state();
            }
        }
        return "Drop";
    }

}
