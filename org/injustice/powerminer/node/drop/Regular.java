/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.node.drop;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;
import org.powerbot.script.wrappers.Item;

/**
 *
 * @author Injustice
 */
public class Regular extends StateNode {
    
    @Override
    public String state() {
        return "Regular dropping";
    }
    private Rock rock;

    public Regular(IMethodContext ctx, Rock rock) {
        super(ctx);
    }

    @Override
    public void execute() {
        for (Item i : ctx.backpack) {
            i.interact("Drop");
        }
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().id(rock.getInvId()).count() > 0;
    }

}
