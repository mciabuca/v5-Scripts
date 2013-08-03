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
import org.injustice.powerminer.data.RockFilter;
import org.powerbot.script.wrappers.GameObject;

/**
 *
 * @author Injustice
 */
public class MineBranch extends Branch {
    private final int[] unacceptableItems =  {
        1621, 1617, 1619, 1623, 1625, 
        1627, 1629, 1631, 21345, 6979, 6981, 6983 
    };
    
    private StateNode[] nodes;
    private Rock rock;

    public MineBranch(IMethodContext ctx, StateNode[] nodes, Rock rock) {
        super(ctx, nodes);
        this.rock = rock;
        this.nodes = nodes;
    }

    @Override
    public boolean branch() {
        if (!ctx.backpack.isFull() && !ctx.backpack.containsAnyOf(unacceptableItems)) {
            for (GameObject g : ctx.objects.select().select(new RockFilter(rock)).nearest().first()) {
                if (g.isValid()) {
                    return true;
                }
            }
        }
        return false;
    };

    @Override
    public String state() {
        for (StateNode n : nodes) {
            if (n.isAlive()) {
                return n.state();
            }
        }
        return "Mine";
    }
}
