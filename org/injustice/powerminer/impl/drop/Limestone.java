/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.impl.drop;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.Node;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;
import org.powerbot.script.util.Delay;

/**
 *
 * @author Injustice
 */
public class Limestone extends StateNode {
    
    @Override
    public String state() {
        return "Dropping limestone";
    }

    public Limestone(IMethodContext ctx) {
        super(ctx);
    }
    @Override
    public void execute() {
        while(ctx.backpack.containsAnyOf(Rock.LIMESTONE.getInvId())) {
            ctx.combatBar.getActionAt(1).getComponent().interact("Drop");
            Delay.sleep(150, 300);
        }
    }

    @Override
    public boolean activate() {
        return !ctx.backpack.select().id(Rock.LIMESTONE.getInvId()).isEmpty();
               
    }

    

}
