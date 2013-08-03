/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.impl;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;
import org.injustice.powerminer.impl.drop.Actionbar;
import org.injustice.powerminer.impl.drop.Gems;
import org.injustice.powerminer.impl.drop.Granite;
import org.injustice.powerminer.impl.drop.Limestone;
import org.injustice.powerminer.impl.drop.Mousekeys;
import org.injustice.powerminer.impl.drop.Regular;

/**
 *
 * @author Injustice
 */
public class Drop extends StateNode {

    private IMethodContext ctx;
    private Rock rock;
    private String status;

    private StateNode[] nodes = new StateNode[] {new Actionbar(ctx, rock),
            new Gems(ctx),
            new Granite(ctx),
            new Limestone(ctx),
            new Mousekeys(ctx, rock),
            new Regular(ctx, rock)};
    
    private final int[] priorityItems =  {
        1621, 1617, 1619, 1623, 1625, 
        1627, 1629, 1631, 21345, 6979, 6981, 6983 
    };
    
    public Drop(IMethodContext ctx, Rock rock) {
        super(ctx);
        this.rock = rock;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.containsAnyOf(priorityItems) ||
                (ctx.backpack.isFull() && !ctx.backpack.select().id(rock.getInvId()).isEmpty());    }

    @Override
    public void execute() {
        for (StateNode stateNode : nodes) {
            if (stateNode.activate()) {
                stateNode.execute();
                status = stateNode.state();
            }
        }
    }
    
    @Override
    public String state() {
        return status;
    }
}
